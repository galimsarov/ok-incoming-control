package ru.otus.otuskotlin.incomingControl.biz.general

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.models.IctrlWorkMode
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.chain

fun ICorChainDsl<IctrlContext>.stubs(title: String, block: ICorChainDsl<IctrlContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == IctrlWorkMode.STUB && state == IctrlState.RUNNING }
}