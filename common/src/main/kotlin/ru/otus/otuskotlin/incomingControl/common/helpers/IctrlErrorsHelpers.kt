package ru.otus.otuskotlin.incomingControl.common.helpers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState

fun Throwable.asIctrlError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = IctrlError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun IctrlContext.addError(vararg error: IctrlError) = errors.addAll(error)

fun IctrlContext.fail(error: IctrlError) {
    addError(error)
    state = IctrlState.FAILING
}