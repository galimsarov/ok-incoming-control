package ru.otus.otuskotlin.incomingControl.biz.workers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.stubs.IctrlStubs
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub

fun ICorChainDsl<IctrlContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == IctrlStubs.SUCCESS && state == IctrlState.RUNNING }
    handle {
        state = IctrlState.FINISHING
        commoditiesResponse.addAll(IctrlCommodityStub.prepareSearchList(commodityFilterRequest.searchString))
    }
}