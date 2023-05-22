package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.errorValidation
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityLock
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в IctrlCommodityId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { commodityValidating.lock != IctrlCommodityLock.NONE && !commodityValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = commodityValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}