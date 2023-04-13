package ru.otus.otuskotlin.incomingControl.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.incomingControl.api.v1.models.*

suspend fun ApplicationCall.createCommodity() = processV1<CommodityCreateRequest>()

suspend fun ApplicationCall.readCommodity() = processV1<CommodityReadRequest>()

suspend fun ApplicationCall.updateCommodity() = processV1<CommodityUpdateRequest>()

suspend fun ApplicationCall.deleteCommodity() = processV1<CommodityDeleteRequest>()

suspend fun ApplicationCall.searchCommodity() = processV1<CommoditySearchRequest>()