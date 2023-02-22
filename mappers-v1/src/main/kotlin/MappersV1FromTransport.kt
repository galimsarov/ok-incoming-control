import exceptions.UnknownRequestClass
import models.*
import ru.otus.otuskotlin.api.v1.models.*
import stubs.IctrlStubs

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

private fun CommoditySearchFilter?.toInternal(): MkplAdFilter = MkplAdFilter(
    searchString = this?.searchString ?: ""
)