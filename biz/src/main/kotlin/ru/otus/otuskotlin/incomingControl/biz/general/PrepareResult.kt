package ru.otus.otuskotlin.incomingControl.biz.general

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.models.IctrlWorkMode
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != IctrlWorkMode.STUB }
    handle {
        commodityResponse = commodityRepoDone
        commoditiesResponse = commoditiesRepoDone
        state = when (val st = state) {
            IctrlState.RUNNING -> IctrlState.FINISHING
            else -> st
        }
    }
}