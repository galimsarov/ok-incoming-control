package ru.otus.otuskotlin.springapp.api.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.common.IctrlContext
import ru.otus.otuskotlin.mappers.v1.*
import ru.otus.otuskotlin.stubs.IctrlCommodityStub

@RestController
@RequestMapping("v1/commodity")
class CommodityController {
    @PostMapping("create")
    fun createCommodity(@RequestBody request: CommodityCreateRequest): CommodityCreateResponse {
        val context = IctrlContext()
        context.fromTransport(request)
        context.commodityResponse = IctrlCommodityStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readCommodity(@RequestBody request: CommodityReadRequest): CommodityReadResponse {
        val context = IctrlContext()
        context.fromTransport(request)
        context.commodityResponse = IctrlCommodityStub.get()
        return context.toTransportRead()
    }

    @PostMapping("update")
    fun updateCommodity(@RequestBody request: CommodityUpdateRequest): CommodityUpdateResponse {
        val context = IctrlContext()
        context.fromTransport(request)
        context.commodityResponse = IctrlCommodityStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteCommodity(@RequestBody request: CommodityDeleteRequest): CommodityDeleteResponse {
        val context = IctrlContext()
        context.fromTransport(request)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchCommodity(@RequestBody request: CommoditySearchRequest): CommoditySearchResponse {
        val context = IctrlContext()
        context.fromTransport(request)
        context.commoditiesResponse.add(IctrlCommodityStub.get())
        return context.toTransportSearch()
    }
}