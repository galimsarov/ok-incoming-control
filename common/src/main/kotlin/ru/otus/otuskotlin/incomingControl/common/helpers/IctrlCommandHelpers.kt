package ru.otus.otuskotlin.incomingControl.common.helpers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand

fun IctrlContext.isUpdatableCommand() =
    this.command in listOf(IctrlCommand.CREATE, IctrlCommand.UPDATE, IctrlCommand.DELETE)