package models

data class IctrlCommodity(
    var id: IctrlCommodityId = IctrlCommodityId.NONE,
    var name: String = "",
    var description: String = "",
    var receiptQuantity: String = "",
    var ownerId: IctrlUserId = IctrlUserId.NONE,
    var visibility: IctrlVisibility = IctrlVisibility.NONE,
    var productId: IctrlProductId = IctrlProductId.NONE, // WTF?
    val permissionsClient: MutableSet<IctrlCommodityPermissionClient> = mutableSetOf()
)
