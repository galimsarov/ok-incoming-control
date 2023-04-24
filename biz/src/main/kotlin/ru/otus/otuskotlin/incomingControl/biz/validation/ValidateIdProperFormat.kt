package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.errorValidation
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { commodityValidating.id != IctrlCommodityId.NONE && !commodityValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = commodityValidating.id.asString()
                .replace("<", "&lt;")
                .replace(">", "&gt;")
        fail(
                errorValidation(
                        field = "id",
                        violationCode = "badFormat",
                        description = "value $encodedId must contain only letters and numbers"
                )
        )
    }
}