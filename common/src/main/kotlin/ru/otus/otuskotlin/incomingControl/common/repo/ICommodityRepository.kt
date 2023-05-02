package ru.otus.otuskotlin.incomingControl.common.repo

interface ICommodityRepository {
    suspend fun createAd(rq: DbCommodityRequest): DbCommodityResponse
    suspend fun readCommodity(rq: DbCommodityIdRequest): DbCommodityResponse
    suspend fun updateCommodity(rq: DbCommodityRequest): DbCommodityResponse
    suspend fun deleteCommodity(rq: DbCommodityIdRequest): DbCommodityResponse
    suspend fun searchCommodity(rq: DbCommodityFilterRequest): DbCommoditiesResponse
}