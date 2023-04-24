package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.errorValidation
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.validateManufacturerHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { commodityValidating.manufacturer.isNotEmpty() && !commodityValidating.manufacturer.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "manufacturer",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}