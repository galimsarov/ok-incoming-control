package ru.otus.otuskotlin.incomingControl.biz.workers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == IctrlState.RUNNING }
    handle {
        fail(
                IctrlError(
                        code = "validation",
                        field = "stub",
                        group = "validation",
                        message = "Wrong stub case is requested: ${stubCase.name}"
                )
        )
    }
}