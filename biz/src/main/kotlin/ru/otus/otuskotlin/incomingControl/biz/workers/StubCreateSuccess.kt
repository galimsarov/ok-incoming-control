package ru.otus.otuskotlin.incomingControl.biz.workers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityType
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.models.IctrlVisibility
import ru.otus.otuskotlin.incomingControl.common.stubs.IctrlStubs
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub

fun ICorChainDsl<IctrlContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == IctrlStubs.SUCCESS && state == IctrlState.RUNNING }
    handle {
        state = IctrlState.FINISHING
        val stub = IctrlCommodityStub.prepareResult {
            commodityRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            commodityRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            commodityRequest.manufacturer.takeIf { it.isNotBlank() }?.also { this.manufacturer = it }
            commodityRequest.receiptQuantity.takeIf { it.isNotBlank() }?.also { this.receiptQuantity = it }
            commodityRequest.commodityType.takeIf { it != IctrlCommodityType.NONE }?.also { this.commodityType = it }
            commodityRequest.visibility.takeIf { it != IctrlVisibility.NONE }?.also { this.visibility = it }
        }
        commodityResponse = stub
    }
}