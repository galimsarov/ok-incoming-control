package ru.otus.otuskotlin.incomingControl.repo.inmemory

import ru.otus.otuskotlin.incomingControl.common.repo.*
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub

class CommodityRepoStub : ICommodityRepository {
    override suspend fun createCommodity(rq: DbCommodityRequest): DbCommodityResponse {
        return DbCommodityResponse(
            data = IctrlCommodityStub.prepareResult { },
            isSuccess = true
        )
    }

    override suspend fun readCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
        return DbCommodityResponse(
            data = IctrlCommodityStub.prepareResult { },
            isSuccess = true
        )
    }

    override suspend fun updateCommodity(rq: DbCommodityRequest): DbCommodityResponse {
        return DbCommodityResponse(
            data = IctrlCommodityStub.prepareResult { },
            isSuccess = true
        )
    }

    override suspend fun deleteCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
        return DbCommodityResponse(
            data = IctrlCommodityStub.prepareResult { },
            isSuccess = true
        )
    }

    override suspend fun searchCommodity(rq: DbCommodityFilterRequest): DbCommoditiesResponse {
        return DbCommoditiesResponse(
            data = IctrlCommodityStub.prepareSearchList(filter = ""),
            isSuccess = true
        )
    }
}