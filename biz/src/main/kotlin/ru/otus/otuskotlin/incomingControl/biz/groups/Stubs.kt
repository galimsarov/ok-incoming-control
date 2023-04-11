package ru.otus.otuskotlin.incomingControl.biz.groups

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.models.IctrlWorkMode
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.chain

fun ICorChainDsl<IctrlContext>.stubs(name: String, block: ICorChainDsl<IctrlContext>.() -> Unit) = chain {
    block()
    this.name = name
    on { workMode == IctrlWorkMode.STUB && state == IctrlState.RUNNING }
}