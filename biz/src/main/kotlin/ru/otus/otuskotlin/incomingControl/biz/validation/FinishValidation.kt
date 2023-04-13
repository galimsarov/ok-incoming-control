package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.finishCommodityValidation(title: String) = worker {
    this.title = title
    on { state == IctrlState.RUNNING }
    handle {
        commodityValidated = commodityValidating
    }
}

fun ICorChainDsl<IctrlContext>.finishCommodityFilterValidation(title: String) = worker {
    this.title = title
    on { state == IctrlState.RUNNING }
    handle {
        commodityFilterValidated = commodityFilterValidating
    }
}