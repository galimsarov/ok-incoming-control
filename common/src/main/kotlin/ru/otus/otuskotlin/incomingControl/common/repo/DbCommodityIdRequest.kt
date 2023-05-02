package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId

data class DbCommodityIdRequest(
    val id: IctrlCommodityId
) {
    constructor(commodity: IctrlCommodity) : this(commodity.id)
}
