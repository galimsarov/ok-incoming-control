package ru.otus.otuskotlin.incomingControl.common.helpers

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.exceptions.RepoConcurrencyException
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityLock
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

fun errorValidation(
    field: String,
    /**
         * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
         * Например: empty, badSymbols, tooLong, etc
         */
    violationCode: String,
    description: String,
    level: IctrlError.Level = IctrlError.Level.ERROR,
) = IctrlError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: IctrlError.Level = IctrlError.Level.ERROR,
) = IctrlError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: IctrlCommodityLock,
    actualLock: IctrlCommodityLock?,
    exception: Exception? = null,
) = IctrlError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)