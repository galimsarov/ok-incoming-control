package ru.otus.otuskotlin.incomingControl.biz.workers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.stubs.IctrlStubs
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == IctrlStubs.DB_ERROR && state == IctrlState.RUNNING }
    handle {
        state = IctrlState.FAILING
        this.errors.add(
                IctrlError(
                        group = "internal",
                        code = "internal-db",
                        message = "Internal error"
                )
        )
    }
}