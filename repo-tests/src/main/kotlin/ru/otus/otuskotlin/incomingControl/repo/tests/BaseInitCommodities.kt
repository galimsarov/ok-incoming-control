package ru.otus.otuskotlin.incomingControl.repo.tests

import ru.otus.otuskotlin.incomingControl.common.models.*

abstract class BaseInitCommodities(val op: String) : IInitObjects<IctrlCommodity> {
    fun createInitTestModel(
        suf: String,
        ownerId: IctrlUserId = IctrlUserId("owner-123"),
    ) = IctrlCommodity(
        id = IctrlCommodityId("commodity-repo-$op-$suf"),
        name = "$suf stub",
        description = "$suf stub description",
        manufacturer = "$suf stub manufacturer",
        receiptQuantity = "123",
        commodityType = IctrlCommodityType.FASTENER_PART,
        ownerId = ownerId,
        visibility = IctrlVisibility.VISIBLE_TO_OWNER,
    )
}
