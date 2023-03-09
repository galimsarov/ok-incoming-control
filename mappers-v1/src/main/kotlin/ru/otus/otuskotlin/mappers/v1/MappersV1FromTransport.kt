package ru.otus.otuskotlin.mappers.v1

import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.common.IctrlContext
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.common.stubs.IctrlStubs
import ru.otus.otuskotlin.mappers.v1.exceptions.UnknownRequestClass

fun IctrlContext.fromTransport(request: IRequest) = when (request) {
    is CommodityCreateRequest -> fromTransport(request)
    is CommodityReadRequest -> fromTransport(request)
    is CommodityUpdateRequest -> fromTransport(request)
    is CommodityDeleteRequest -> fromTransport(request)
    is CommoditySearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toCommodityId() = this?.let { IctrlCommodityId(it) } ?: IctrlCommodityId.NONE
private fun String?.toCommodityWithId() = IctrlCommodity(id = this.toCommodityId())
private fun IRequest?.requestId() = this?.requestId?.let { IctrlRequestId(it) } ?: IctrlRequestId.NONE

private fun CommodityDebug?.transportToWorkMode(): IctrlWorkMode = when (this?.mode) {
    CommodityRequestDebugMode.PROD -> IctrlWorkMode.PROD
    CommodityRequestDebugMode.TEST -> IctrlWorkMode.TEST
    CommodityRequestDebugMode.STUB -> IctrlWorkMode.STUB
    null -> IctrlWorkMode.PROD
}

private fun CommodityDebug?.transportToStubCase(): IctrlStubs = when (this?.stub) {
    CommodityRequestDebugStubs.SUCCESS -> IctrlStubs.SUCCESS
    CommodityRequestDebugStubs.NOT_FOUND -> IctrlStubs.NOT_FOUND
    CommodityRequestDebugStubs.BAD_ID -> IctrlStubs.BAD_ID
    CommodityRequestDebugStubs.BAD_NAME -> IctrlStubs.BAD_NAME
    CommodityRequestDebugStubs.BAD_DESCRIPTION -> IctrlStubs.BAD_DESCRIPTION
    CommodityRequestDebugStubs.BAD_VISIBILITY -> IctrlStubs.BAD_VISIBILITY
    CommodityRequestDebugStubs.CANNOT_DELETE -> IctrlStubs.CANNOT_DELETE
    CommodityRequestDebugStubs.BAD_SEARCH_STRING -> IctrlStubs.BAD_SEARCH_STRING
    null -> IctrlStubs.NONE
}

fun IctrlContext.fromTransport(request: CommodityCreateRequest) {
    command = IctrlCommand.CREATE
    requestId = request.requestId()
    commodityRequest = request.commodity?.toInternal() ?: IctrlCommodity()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun IctrlContext.fromTransport(request: CommodityReadRequest) {
    command = IctrlCommand.READ
    requestId = request.requestId()
    commodityRequest = request.commodity?.id.toCommodityWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun IctrlContext.fromTransport(request: CommodityUpdateRequest) {
    command = IctrlCommand.UPDATE
    requestId = request.requestId()
    commodityRequest = request.commodity?.toInternal() ?: IctrlCommodity()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun IctrlContext.fromTransport(request: CommodityDeleteRequest) {
    command = IctrlCommand.DELETE
    requestId = request.requestId()
    commodityRequest = request.commodity?.id.toCommodityWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun IctrlContext.fromTransport(request: CommoditySearchRequest) {
    command = IctrlCommand.SEARCH
    requestId = request.requestId()
    commodityFilterRequest = request.commodityFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun CommoditySearchFilter?.toInternal(): IctrlCommodityFilter = IctrlCommodityFilter(
    searchString = this?.searchString ?: ""
)

private fun CommodityCreateObject.toInternal(): IctrlCommodity = IctrlCommodity(
    name = this.name ?: "",
    description = this.description ?: "",
    manufacturer = this.manufacturer ?: "",
    receiptQuantity = this.receiptQuantity ?: "",
    commodityType = this.commodityType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun CommodityUpdateObject.toInternal(): IctrlCommodity = IctrlCommodity(
    id = this.id.toCommodityId(),
    name = this.name ?: "",
    description = this.description ?: "",
    manufacturer = this.manufacturer ?: "",
    receiptQuantity = this.receiptQuantity ?: "",
    commodityType = this.commodityType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun CommodityVisibility?.fromTransport(): IctrlVisibility = when (this) {
    CommodityVisibility.PUBLIC -> IctrlVisibility.VISIBLE_PUBLIC
    CommodityVisibility.OWNER_ONLY -> IctrlVisibility.VISIBLE_TO_OWNER
    CommodityVisibility.REGISTERED_ONLY -> IctrlVisibility.VISIBLE_TO_GROUP
    null -> IctrlVisibility.NONE
}

private fun CommodityType?.fromTransport(): IctrlCommodityType = when (this) {
    CommodityType.TUBE_LINE_PART -> IctrlCommodityType.TUBE_LINE_PART
    CommodityType.FASTENER_PART -> IctrlCommodityType.FASTENER_PART
    CommodityType.COATING_MATERIAL -> IctrlCommodityType.COATING_MATERIAL
    null -> IctrlCommodityType.NONE
}