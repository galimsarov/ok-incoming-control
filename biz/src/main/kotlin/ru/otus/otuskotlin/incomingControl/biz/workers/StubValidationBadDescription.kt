package ru.otus.otuskotlin.incomingControl.biz.workers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.stubs.IctrlStubs
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.stubValidationBadDescription(name: String) = worker {
    this.name = name
    on { stubCase == IctrlStubs.BAD_DESCRIPTION && state == IctrlState.RUNNING }
    handle {
        state = IctrlState.FAILING
        this.errors.add(
            IctrlError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}