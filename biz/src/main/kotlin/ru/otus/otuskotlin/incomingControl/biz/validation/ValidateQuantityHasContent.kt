package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.errorValidation
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.validateQuantityHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\d+[.,]?\\d*")
    on { commodityValidating.receiptQuantity.isNotEmpty() && !commodityValidating.receiptQuantity.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "receiptQuantity",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}