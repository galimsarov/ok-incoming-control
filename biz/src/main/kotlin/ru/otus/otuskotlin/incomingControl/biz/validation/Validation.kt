package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.chain

fun ICorChainDsl<IctrlContext>.validation(block: ICorChainDsl<IctrlContext>.() -> Unit) = chain {
    block()
    title = "Валидация"
    on { state == IctrlState.RUNNING }
}