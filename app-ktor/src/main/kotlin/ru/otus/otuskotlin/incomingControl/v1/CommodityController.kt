package ru.otus.otuskotlin.incomingControl.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.incomingControl.IctrlAppSettings
import ru.otus.otuskotlin.incomingControl.api.v1.models.*

suspend fun ApplicationCall.createCommodity(appSettings: IctrlAppSettings) =
    processV1<CommodityCreateRequest>(appSettings)

suspend fun ApplicationCall.readCommodity(appSettings: IctrlAppSettings) = processV1<CommodityReadRequest>(appSettings)

suspend fun ApplicationCall.updateCommodity(appSettings: IctrlAppSettings) =
    processV1<CommodityUpdateRequest>(appSettings)

suspend fun ApplicationCall.deleteCommodity(appSettings: IctrlAppSettings) =
    processV1<CommodityDeleteRequest>(appSettings)

suspend fun ApplicationCall.searchCommodity(appSettings: IctrlAppSettings) =
    processV1<CommoditySearchRequest>(appSettings)