package exceptions

import models.IctrlCommand

class UnknownIctrlCommand(command: IctrlCommand) : Throwable("Wrong command $command at mapping toTransport stage")