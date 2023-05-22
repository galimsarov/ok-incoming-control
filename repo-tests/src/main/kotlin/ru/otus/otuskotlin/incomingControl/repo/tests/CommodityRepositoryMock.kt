package ru.otus.otuskotlin.incomingControl.repo.tests

import ru.otus.otuskotlin.incomingControl.common.repo.*

class CommodityRepositoryMock(
    private val invokeCreateCommodity: (DbCommodityRequest) -> DbCommodityResponse = { DbCommodityResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadCommodity: (DbCommodityIdRequest) -> DbCommodityResponse = { DbCommodityResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateCommodity: (DbCommodityRequest) -> DbCommodityResponse = { DbCommodityResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteCommodity: (DbCommodityIdRequest) -> DbCommodityResponse = { DbCommodityResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchCommodity: (DbCommodityFilterRequest) -> DbCommoditiesResponse = { DbCommoditiesResponse.MOCK_SUCCESS_EMPTY },
) : ICommodityRepository {
    override suspend fun createCommodity(rq: DbCommodityRequest): DbCommodityResponse {
        return invokeCreateCommodity(rq)
    }

    override suspend fun readCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
        return invokeReadCommodity(rq)
    }

    override suspend fun updateCommodity(rq: DbCommodityRequest): DbCommodityResponse {
        return invokeUpdateCommodity(rq)
    }

    override suspend fun deleteCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
        return invokeDeleteCommodity(rq)
    }

    override suspend fun searchCommodity(rq: DbCommodityFilterRequest): DbCommoditiesResponse {
        return invokeSearchCommodity(rq)
    }
}