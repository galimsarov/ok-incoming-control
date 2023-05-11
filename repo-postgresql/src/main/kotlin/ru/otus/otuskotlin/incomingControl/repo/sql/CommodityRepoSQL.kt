package ru.otus.otuskotlin.incomingControl.repo.sql

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.incomingControl.common.helpers.asIctrlError
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityLock
import ru.otus.otuskotlin.incomingControl.common.models.IctrlUserId
import ru.otus.otuskotlin.incomingControl.common.repo.*
import java.util.*

class CommodityRepoSQL(
    properties: SqlProperties,
    initObjects: List<IctrlCommodity> = emptyList(),
    val randomUuid: () -> String = { UUID.randomUUID().toString() },
) : ICommodityRepository {
    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(properties.url, driver, properties.user, properties.password)

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(CommodityTable)
            SchemaUtils.create(CommodityTable)
            initObjects.forEach { createCommodity(it) }
        }
    }

    private fun createCommodity(commodity: IctrlCommodity): IctrlCommodity {
        val res = CommodityTable.insert { to(it, commodity, randomUuid) }
        return CommodityTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction { block() }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbCommodityResponse): DbCommodityResponse =
        transactionWrapper(block) { DbCommodityResponse.error(it.asIctrlError()) }

    override suspend fun createCommodity(rq: DbCommodityRequest): DbCommodityResponse =
        transactionWrapper { DbCommodityResponse.success(createCommodity(rq.commodity)) }

    private fun read(id: IctrlCommodityId): DbCommodityResponse {
        val res = CommodityTable.select {
            CommodityTable.id eq id.asString()
        }.singleOrNull() ?: return DbCommodityResponse.errorNotFound
        return DbCommodityResponse.success(CommodityTable.from(res))
    }

    override suspend fun readCommodity(rq: DbCommodityIdRequest): DbCommodityResponse =
        transactionWrapper { read(rq.id) }

    private fun update(
        id: IctrlCommodityId,
        lock: IctrlCommodityLock,
        block: (IctrlCommodity) -> DbCommodityResponse
    ): DbCommodityResponse =
        transactionWrapper {
            if (id == IctrlCommodityId.NONE) return@transactionWrapper DbCommodityResponse.errorEmptyId

            val current = CommodityTable.select { CommodityTable.id eq id.asString() }
                .firstOrNull()
                ?.let { CommodityTable.from(it) }

            when {
                current == null -> DbCommodityResponse.errorNotFound
                current.lock != lock -> DbCommodityResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateCommodity(rq: DbCommodityRequest): DbCommodityResponse =
        update(rq.commodity.id, rq.commodity.lock) {
            CommodityTable.update({ CommodityTable.id eq rq.commodity.id.asString() }) {
                to(it, rq.commodity, randomUuid)
            }
            read(rq.commodity.id)
        }

    override suspend fun deleteCommodity(rq: DbCommodityIdRequest): DbCommodityResponse = update(rq.id, rq.lock) {
        CommodityTable.deleteWhere { id eq rq.id.asString() }
        DbCommodityResponse.success(it)
    }

    override suspend fun searchCommodity(rq: DbCommodityFilterRequest): DbCommoditiesResponse =
        transactionWrapper({
            val res = CommodityTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != IctrlUserId.NONE) {
                        add(CommodityTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.nameFilter.isNotBlank()) {
                        add((CommodityTable.name like "%${rq.nameFilter}%") or (CommodityTable.description like "%${rq.nameFilter}%"))
                    }
                }.reduce { a, b -> a and b }
            }
            DbCommoditiesResponse(data = res.map { CommodityTable.from(it) }, isSuccess = true)
        }, {
            DbCommoditiesResponse.error(it.asIctrlError())
        })
}