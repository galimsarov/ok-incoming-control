package ru.otus.otuskotlin.incomingControl.biz.groups

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.chain

fun ICorChainDsl<IctrlContext>.operation(
    name: String, command: IctrlCommand, block: ICorChainDsl<IctrlContext>.() -> Unit
) = chain {
    block()
    this.name = name
    on { this.command == command && state == IctrlState.RUNNING }
}