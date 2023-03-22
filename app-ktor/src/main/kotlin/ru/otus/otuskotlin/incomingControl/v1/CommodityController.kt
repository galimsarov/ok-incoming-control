package ru.otus.otuskotlin.incomingControl.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.incomingControl.api.v1.models.*
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.mappers.v1.*
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub

suspend fun ApplicationCall.createCommodity() {
    val request = receive<CommodityCreateRequest>()
    val context = IctrlContext()
    context.fromTransport(request)
    context.commodityResponse = IctrlCommodityStub.get()
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readCommodity() {
    val request = receive<CommodityReadRequest>()
    val context = IctrlContext()
    context.fromTransport(request)
    context.commodityResponse = IctrlCommodityStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateCommodity() {
    val request = receive<CommodityUpdateRequest>()
    val context = IctrlContext()
    context.fromTransport(request)
    context.commodityResponse = IctrlCommodityStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteCommodity() {
    val request = receive<CommodityDeleteRequest>()
    val context = IctrlContext()
    context.fromTransport(request)
    context.commodityResponse = IctrlCommodityStub.get()
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchCommodity() {
    val request = receive<CommoditySearchRequest>()
    val context = IctrlContext()
    context.fromTransport(request)
    context.commoditiesResponse.addAll(IctrlCommodityStub.prepareSearchList("Болт"))
    respond(context.toTransportSearch())
}