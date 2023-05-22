package ru.otus.otuskotlin.incomingControl.biz.repo

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityIdRequest
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение материала из БД"
    on { state == IctrlState.RUNNING }
    handle {
        val request = DbCommodityIdRequest(commodityValidated)
        val result = commodityRepo.readCommodity(request)
        val resultCommodity = result.data
        if (result.isSuccess && resultCommodity != null) {
            commodityRepoRead = resultCommodity
        } else {
            state = IctrlState.FAILING
            errors.addAll(result.errors)
        }
    }
}