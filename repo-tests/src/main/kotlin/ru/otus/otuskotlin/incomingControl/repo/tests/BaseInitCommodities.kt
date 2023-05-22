package ru.otus.otuskotlin.incomingControl.repo.tests

import ru.otus.otuskotlin.incomingControl.common.models.*

abstract class BaseInitCommodities(val op: String) : IInitObjects<IctrlCommodity> {
    open val lockOld: IctrlCommodityLock = IctrlCommodityLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: IctrlCommodityLock = IctrlCommodityLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: IctrlUserId = IctrlUserId("owner-123"),
        lock: IctrlCommodityLock = lockOld,
    ) = IctrlCommodity(
        id = IctrlCommodityId("commodity-repo-$op-$suf"),
        name = "$suf stub",
        description = "$suf stub description",
        manufacturer = "$suf stub manufacturer",
        receiptQuantity = "123",
        commodityType = IctrlCommodityType.FASTENER_PART,
        ownerId = ownerId,
        visibility = IctrlVisibility.VISIBLE_TO_OWNER,
        lock = lock,
    )
}
