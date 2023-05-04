package ru.otus.otuskotlin.incomingControl.biz.repo

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityIdRequest
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление материала из БД по ID"
    on { state == IctrlState.RUNNING }
    handle {
        val request = DbCommodityIdRequest(commodityRepoPrepare)
        val result = commodityRepo.deleteCommodity(request)
        if (!result.isSuccess) {
            state = IctrlState.FAILING
            errors.addAll(result.errors)
        }
        commodityRepoDone = commodityRepoRead
    }
}