package ru.otus.otuskotlin.incomingControl.biz.repo

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityFilterRequest
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск материала в БД по фильтру"
    on { state == IctrlState.RUNNING }
    handle {
        val request = DbCommodityFilterRequest(
            nameFilter = commodityFilterValidated.searchString,
            ownerId = commodityFilterValidated.ownerId,
        )
        val result = commodityRepo.searchCommodity(request)
        val resultCommodities = result.data
        if (result.isSuccess && resultCommodities != null) {
            commoditiesRepoDone = resultCommodities.toMutableList()
        } else {
            state = IctrlState.FAILING
            errors.addAll(result.errors)
        }
    }
}
