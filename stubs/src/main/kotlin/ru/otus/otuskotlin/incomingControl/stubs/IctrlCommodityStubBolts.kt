package ru.otus.otuskotlin.incomingControl.stubs

import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlCommodityPermissionClient

object IctrlCommodityStubBolts {
    val COMMODITY_BOLT = IctrlCommodity(
        id = IctrlCommodityId("123"),
        name = "Болт",
        description = "Болт 100x5",
        manufacturer = "Кунгурский завод металлоизделий",
        receiptQuantity = "1000",
        commodityType = IctrlCommodityType.FASTENER_PART,
        ownerId = IctrlUserId("user-1"),
        visibility = IctrlVisibility.VISIBLE_PUBLIC,
        permissionsClient = mutableSetOf(
            IctrlCommodityPermissionClient.READ,
            IctrlCommodityPermissionClient.UPDATE,
            IctrlCommodityPermissionClient.DELETE,
            IctrlCommodityPermissionClient.MAKE_VISIBLE_PUBLIC,
            IctrlCommodityPermissionClient.MAKE_VISIBLE_GROUP,
            IctrlCommodityPermissionClient.MAKE_VISIBLE_OWNER
        )
    )
}