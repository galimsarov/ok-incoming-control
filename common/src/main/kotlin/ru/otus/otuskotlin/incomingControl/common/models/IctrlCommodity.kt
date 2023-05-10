package ru.otus.otuskotlin.incomingControl.common.models

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
    var lock: IctrlCommodityLock = IctrlCommodityLock.NONE,
    val permissionsClient: MutableSet<IctrlCommodityPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): IctrlCommodity = copy(permissionsClient = permissionsClient.toMutableSet())

    companion object {
        val NONE = IctrlCommodity()
    }
}