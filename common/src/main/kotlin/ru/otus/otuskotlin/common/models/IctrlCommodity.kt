package ru.otus.otuskotlin.common.models

data class IctrlCommodity(
    var id: IctrlCommodityId = IctrlCommodityId.NONE,
    var name: String = "",
    var description: String = "",
    var manufacturer: String = "",
    var receiptQuantity: String = "",
    var commodityType: IctrlCommodityType = IctrlCommodityType.NONE,
    var ownerId: IctrlUserId = IctrlUserId.NONE,
    var visibility: IctrlVisibility = IctrlVisibility.NONE,
    var productId: IctrlProductId = IctrlProductId.NONE,
    val permissionsClient: MutableSet<IctrlCommodityPermissionClient> = mutableSetOf()
)
