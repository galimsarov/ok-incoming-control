package ru.otus.otuskotlin.incomingControl.stubs

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStubBolts.COMMODITY_BOLT

object IctrlCommodityStub {
    fun get(): IctrlCommodity = COMMODITY_BOLT

    fun prepareSearchList(filter: String) = listOf(
        ictrlCommodity("d-666-01", filter),
        ictrlCommodity("d-666-02", filter),
        ictrlCommodity("d-666-03", filter),
        ictrlCommodity("d-666-04", filter),
        ictrlCommodity("d-666-05", filter),
        ictrlCommodity("d-666-06", filter),
    )

    private fun ictrlCommodity(id: String, filter: String) = COMMODITY_BOLT.copy(
        id = IctrlCommodityId(id),
        name = "$filter $id",
        description = "desc $filter $id",
    )
}