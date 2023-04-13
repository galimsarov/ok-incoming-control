package ru.otus.otuskotlin.incomingControl.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.stubs.IctrlStubs

data class IctrlContext(
        var command: IctrlCommand = IctrlCommand.NONE,
        var state: IctrlState = IctrlState.NONE,
        var errors: MutableList<IctrlError> = mutableListOf(),

        var workMode: IctrlWorkMode = IctrlWorkMode.PROD,
        var stubCase: IctrlStubs = IctrlStubs.NONE,

        var requestId: IctrlRequestId = IctrlRequestId.NONE,
        var timeStart: Instant = Instant.NONE,
        var commodityRequest: IctrlCommodity = IctrlCommodity(),
        var commodityFilterRequest: IctrlCommodityFilter = IctrlCommodityFilter(),

        var commodityValidating: IctrlCommodity = IctrlCommodity(),
        var commodityFilterValidating: IctrlCommodityFilter = IctrlCommodityFilter(),

        var commodityValidated: IctrlCommodity = IctrlCommodity(),
        var commodityFilterValidated: IctrlCommodityFilter = IctrlCommodityFilter(),

        var commodityResponse: IctrlCommodity = IctrlCommodity(),
        var commoditiesResponse: MutableList<IctrlCommodity> = mutableListOf(),
)