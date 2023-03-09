package ru.otus.otuskotlin.mappers.v1.exceptions

import ru.otus.otuskotlin.common.models.IctrlCommand

class UnknownIctrlCommand(command: IctrlCommand) : Throwable("Wrong command $command at mapping toTransport stage")