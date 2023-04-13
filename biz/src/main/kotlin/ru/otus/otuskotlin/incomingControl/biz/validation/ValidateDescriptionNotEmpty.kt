package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.errorValidation
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { commodityValidating.description.isEmpty() }
    handle {
        fail(
                errorValidation(
                        field = "description",
                        violationCode = "empty",
                        description = "field must not be empty"
                )
        )
    }
}