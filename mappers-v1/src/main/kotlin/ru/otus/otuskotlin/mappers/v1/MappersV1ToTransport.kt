package ru.otus.otuskotlin.mappers.v1

import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.common.IctrlContext
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.mappers.v1.exceptions.UnknownIctrlCommand

fun IctrlContext.toTransportCommodity(): IResponse = when (val cmd = command) {
    IctrlCommand.CREATE -> toTransportCreate()
    IctrlCommand.READ -> toTransportRead()
    IctrlCommand.UPDATE -> toTransportUpdate()
    IctrlCommand.DELETE -> toTransportDelete()
    IctrlCommand.SEARCH -> toTransportSearch()
    IctrlCommand.NONE -> throw UnknownIctrlCommand(cmd)
}

fun IctrlContext.toTransportCreate() = CommodityCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == IctrlState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    commodity = commodityResponse.toTransportCommodity()
)

fun IctrlContext.toTransportRead() = CommodityReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == IctrlState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    commodity = commodityResponse.toTransportCommodity()
)

fun IctrlContext.toTransportUpdate() = CommodityUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == IctrlState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    commodity = commodityResponse.toTransportCommodity()
)

fun IctrlContext.toTransportDelete() = CommodityDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == IctrlState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    commodity = commodityResponse.toTransportCommodity()
)

fun IctrlContext.toTransportSearch() = CommoditySearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == IctrlState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    commodities = commoditiesResponse.toTransportCommodity()
)

fun List<IctrlCommodity>.toTransportCommodity(): List<CommodityResponseObject>? = this
    .map { it.toTransportCommodity() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun IctrlCommodity.toTransportCommodity(): CommodityResponseObject = CommodityResponseObject(
    id = id.takeIf { it != IctrlCommodityId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    manufacturer = manufacturer.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != IctrlUserId.NONE }?.asString(),
    receiptQuantity = receiptQuantity.takeIf { it.isNotBlank() },
    commodityType = commodityType.toTransportCommodity(),
    visibility = visibility.toTransportCommodity(),
    permissions = permissionsClient.toTransportCommodity(),
)

private fun Set<IctrlCommodityPermissionClient>.toTransportCommodity(): Set<CommodityPermissions>? = this
    .map { it.toTransportCommodity() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun IctrlCommodityPermissionClient.toTransportCommodity() = when (this) {
    IctrlCommodityPermissionClient.READ -> CommodityPermissions.READ
    IctrlCommodityPermissionClient.UPDATE -> CommodityPermissions.UPDATE
    IctrlCommodityPermissionClient.MAKE_VISIBLE_OWNER -> CommodityPermissions.MAKE_VISIBLE_OWN
    IctrlCommodityPermissionClient.MAKE_VISIBLE_GROUP -> CommodityPermissions.MAKE_VISIBLE_GROUP
    IctrlCommodityPermissionClient.MAKE_VISIBLE_PUBLIC -> CommodityPermissions.MAKE_VISIBLE_PUBLIC
    IctrlCommodityPermissionClient.DELETE -> CommodityPermissions.DELETE
}

private fun IctrlVisibility.toTransportCommodity(): CommodityVisibility? = when (this) {
    IctrlVisibility.VISIBLE_PUBLIC -> CommodityVisibility.PUBLIC
    IctrlVisibility.VISIBLE_TO_GROUP -> CommodityVisibility.REGISTERED_ONLY
    IctrlVisibility.VISIBLE_TO_OWNER -> CommodityVisibility.OWNER_ONLY
    IctrlVisibility.NONE -> null
}

private fun IctrlCommodityType.toTransportCommodity(): CommodityType? = when (this) {
    IctrlCommodityType.TUBE_LINE_PART -> CommodityType.TUBE_LINE_PART
    IctrlCommodityType.FASTENER_PART -> CommodityType.FASTENER_PART
    IctrlCommodityType.COATING_MATERIAL -> CommodityType.COATING_MATERIAL
    IctrlCommodityType.NONE -> null
}

private fun List<IctrlError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportCommodity() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun IctrlError.toTransportCommodity() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)