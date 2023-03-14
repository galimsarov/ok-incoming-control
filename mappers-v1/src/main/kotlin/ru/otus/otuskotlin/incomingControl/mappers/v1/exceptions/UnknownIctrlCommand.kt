package ru.otus.otuskotlin.incomingControl.mappers.v1.exceptions

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand

class UnknownIctrlCommand(command: IctrlCommand) : Throwable("Wrong command $command at mapping toTransport stage")