package ru.otus.otuskotlin.incomingControl.repo.inmemory.model

import ru.otus.otuskotlin.incomingControl.common.models.*

data class CommodityEntity(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val manufacturer: String? = null,
    val receiptQuantity: String? = null,
    val commodityType: String? = null,
    val ownerId: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: IctrlCommodity) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        manufacturer = model.manufacturer.takeIf { it.isNotBlank() },
        receiptQuantity = model.receiptQuantity.takeIf { it.isNotBlank() },
        commodityType = model.commodityType.takeIf { it != IctrlCommodityType.NONE }?.name,
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        visibility = model.visibility.takeIf { it != IctrlVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = IctrlCommodity(
        id = id?.let { IctrlCommodityId(it) } ?: IctrlCommodityId.NONE,
        name = name ?: "",
        description = description ?: "",
        manufacturer = manufacturer ?: "",
        receiptQuantity = receiptQuantity ?: "",
        commodityType = commodityType?.let { IctrlCommodityType.valueOf(it) } ?: IctrlCommodityType.NONE,
        ownerId = ownerId?.let { IctrlUserId(it) } ?: IctrlUserId.NONE,
        visibility = visibility?.let { IctrlVisibility.valueOf(it) } ?: IctrlVisibility.NONE,
        lock = lock?.let { IctrlCommodityLock(it) } ?: IctrlCommodityLock.NONE,
    )
}
