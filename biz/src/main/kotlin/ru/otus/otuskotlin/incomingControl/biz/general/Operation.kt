package ru.otus.otuskotlin.incomingControl.biz.general

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.chain

fun ICorChainDsl<IctrlContext>.operation(
        title: String, command: IctrlCommand, block: ICorChainDsl<IctrlContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == IctrlState.RUNNING }
}