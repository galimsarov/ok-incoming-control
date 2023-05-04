package ru.otus.otuskotlin.incomingControl.biz.repo

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityRequest
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление материала в БД"
    on { state == IctrlState.RUNNING }
    handle {
        val request = DbCommodityRequest(commodityRepoPrepare)
        val result = commodityRepo.createCommodity(request)
        val resultCommodity = result.data
        if (result.isSuccess && resultCommodity != null) {
            commodityRepoDone = resultCommodity
        } else {
            state = IctrlState.FAILING
            errors.addAll(result.errors)
        }
    }
}