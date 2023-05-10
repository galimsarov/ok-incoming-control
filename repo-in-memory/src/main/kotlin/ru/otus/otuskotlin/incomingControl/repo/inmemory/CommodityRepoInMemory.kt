package ru.otus.otuskotlin.incomingControl.repo.inmemory

import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.incomingControl.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.repo.*
import ru.otus.otuskotlin.incomingControl.repo.inmemory.model.CommodityEntity
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class CommodityRepoInMemory(
    initObjects: List<IctrlCommodity> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { UUID.randomUUID().toString() },
) : ICommodityRepository {
    private val cache = Cache.Builder<String, CommodityEntity>()
        .expireAfterWrite(ttl)
        .build()

    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach { save(it) }
    }

    private fun save(commodity: IctrlCommodity) {
        val entity = CommodityEntity(commodity)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createCommodity(rq: DbCommodityRequest): DbCommodityResponse {
        val key = randomUuid()
        val commodity = rq.commodity.copy(id = IctrlCommodityId(key))
        val entity = CommodityEntity(commodity)
        cache.put(key, entity)
        return DbCommodityResponse(data = commodity, isSuccess = true)
    }

    override suspend fun readCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
        val key = rq.id.takeIf { it != IctrlCommodityId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let { DbCommodityResponse(data = it.toInternal(), isSuccess = true) } ?: resultErrorNotFound
    }

    private suspend fun doUpdate(
        key: String,
        oldLock: String,
        okBlock: (oldCommodity: CommodityEntity) -> DbCommodityResponse
    ): DbCommodityResponse = mutex.withLock {
        val oldCommodity = cache.get(key)
        when {
            oldCommodity == null -> resultErrorNotFound
            oldCommodity.lock != oldLock -> DbCommodityResponse(
                data = oldCommodity.toInternal(),
                isSuccess = false,
                errors = listOf(
                    errorRepoConcurrency(
                        IctrlCommodityLock(oldLock),
                        oldCommodity.lock?.let { IctrlCommodityLock(it) })
                )
            )

            else -> okBlock(oldCommodity)
        }
    }

    override suspend fun updateCommodity(rq: DbCommodityRequest): DbCommodityResponse {
        val key = rq.commodity.id.takeIf { it != IctrlCommodityId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock =
            rq.commodity.lock.takeIf { it != IctrlCommodityLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newCommodity = rq.commodity.copy()
        val entity = CommodityEntity(newCommodity)
        return doUpdate(key, oldLock) {
            cache.put(key, entity)
            DbCommodityResponse(
                data = newCommodity,
                isSuccess = true,
            )
        }
    }

    override suspend fun deleteCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
        val key = rq.id.takeIf { it != IctrlCommodityId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != IctrlCommodityLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return doUpdate(key, oldLock) { oldAd ->
            cache.invalidate(key)
            DbCommodityResponse(
                data = oldAd.toInternal(),
                isSuccess = true,
            )
        }
    }

    override suspend fun searchCommodity(rq: DbCommodityFilterRequest): DbCommoditiesResponse {
        val result: List<IctrlCommodity> = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != IctrlUserId.NONE }?.let { it.asString() == entry.value.ownerId } ?: true
            }
            .filter { entry ->
                rq.nameFilter.takeIf { it.isNotBlank() }?.let { entry.value.name?.contains(it) ?: false } ?: true
            }
            .map { it.value.toInternal() }.toList()
        return DbCommoditiesResponse(data = result, isSuccess = true)
    }

    companion object {
        val resultErrorEmptyId = DbCommodityResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                IctrlError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbCommodityResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                IctrlError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbCommodityResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                IctrlError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}