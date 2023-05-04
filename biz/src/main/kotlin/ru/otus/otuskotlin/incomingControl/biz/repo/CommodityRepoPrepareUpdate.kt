package ru.otus.otuskotlin.incomingControl.biz.repo

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == IctrlState.RUNNING }
    handle {
        commodityRepoPrepare = commodityRepoRead.deepCopy().apply {
            name = commodityValidated.name
            description = commodityValidated.description
            manufacturer = commodityValidated.manufacturer
            receiptQuantity = commodityValidated.receiptQuantity
            commodityType = commodityValidated.commodityType
            visibility = commodityValidated.visibility
        }
    }
}