package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.errorValidation
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { commodityValidating.id.asString().isEmpty() }
    handle {
        fail(
                errorValidation(
                        field = "id",
                        violationCode = "empty",
                        description = "field must not be empty"
                )
        )
    }
}