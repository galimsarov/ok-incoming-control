package models

data class IctrlCommodity(
    var id: IctrlCommodityId = IctrlCommodityId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: IctrlUserId = IctrlUserId.NONE,
    var visibility: IctrlVisibility = IctrlVisibility.NONE,
    var productId: IctrlProductId = IctrlProductId.NONE, // WTF?
    val permissionsClient: MutableSet<IctrlCommodityPermissionClient> = mutableSetOf()
)
