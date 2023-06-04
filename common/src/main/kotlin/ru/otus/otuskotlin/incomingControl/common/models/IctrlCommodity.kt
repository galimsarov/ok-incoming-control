package ru.otus.otuskotlin.incomingControl.common.models

import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlCommodityPermissionClient
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlPrincipalRelations

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
    val permissionsClient: MutableSet<IctrlCommodityPermissionClient> = mutableSetOf(),
    var principalRelations: Set<IctrlPrincipalRelations> = emptySet(),
) {
    fun deepCopy(): IctrlCommodity = copy(
        principalRelations = principalRelations.toSet(),
        permissionsClient = permissionsClient.toMutableSet()
    )

    companion object {
        val NONE = IctrlCommodity()
    }
}