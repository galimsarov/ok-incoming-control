package ru.otus.otuskotlin.incomingControl.common.repo

interface ICommodityRepository {
    suspend fun createCommodity(rq: DbCommodityRequest): DbCommodityResponse
    suspend fun readCommodity(rq: DbCommodityIdRequest): DbCommodityResponse
    suspend fun updateCommodity(rq: DbCommodityRequest): DbCommodityResponse
    suspend fun deleteCommodity(rq: DbCommodityIdRequest): DbCommodityResponse
    suspend fun searchCommodity(rq: DbCommodityFilterRequest): DbCommoditiesResponse

    companion object {
        val NONE = object : ICommodityRepository {
            override suspend fun createCommodity(rq: DbCommodityRequest): DbCommodityResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateCommodity(rq: DbCommodityRequest): DbCommodityResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteCommodity(rq: DbCommodityIdRequest): DbCommodityResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchCommodity(rq: DbCommodityFilterRequest): DbCommoditiesResponse {
                TODO("Not yet implemented")
            }
        }
    }
}