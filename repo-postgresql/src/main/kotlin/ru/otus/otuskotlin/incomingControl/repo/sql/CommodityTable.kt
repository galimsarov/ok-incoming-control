package ru.otus.otuskotlin.incomingControl.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.incomingControl.common.models.*

object CommodityTable : Table("commodity") {
    val id = varchar("id", 128)
    val name = varchar("name", 128)
    val description = varchar("description", 512)
    val manufacturer = varchar("manufacturer", 128)
    val receiptQuantity = varchar("receipt_quantity", 128)
    val commodityType = enumeration("commodity_type", IctrlCommodityType::class)
    val owner = varchar("owner", 128)
    val visibility = enumeration("visibility", IctrlVisibility::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = IctrlCommodity(
        id = IctrlCommodityId(res[id].toString()),
        name = res[name],
        description = res[description],
        manufacturer = res[manufacturer],
        receiptQuantity = res[receiptQuantity],
        commodityType = res[commodityType],
        ownerId = IctrlUserId(res[owner].toString()),
        visibility = res[visibility],
        lock = IctrlCommodityLock(res[lock])
    )

    fun from(res: ResultRow) = IctrlCommodity(
        id = IctrlCommodityId(res[id].toString()),
        name = res[name],
        description = res[description],
        manufacturer = res[manufacturer],
        receiptQuantity = res[receiptQuantity],
        commodityType = res[commodityType],
        ownerId = IctrlUserId(res[owner].toString()),
        visibility = res[visibility],
        lock = IctrlCommodityLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, commodity: IctrlCommodity, randomUuid: () -> String) {
        it[id] = commodity.id.takeIf { it != IctrlCommodityId.NONE }?.asString() ?: randomUuid()
        it[name] = commodity.name
        it[description] = commodity.description
        it[manufacturer] = commodity.manufacturer
        it[receiptQuantity] = commodity.receiptQuantity
        it[commodityType] = commodity.commodityType
        it[owner] = commodity.ownerId.asString()
        it[visibility] = commodity.visibility
        it[lock] = commodity.lock.takeIf { it != IctrlCommodityLock.NONE }?.asString() ?: randomUuid()
    }
}