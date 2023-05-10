package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityLock

data class DbCommodityIdRequest(
    val id: IctrlCommodityId,
    val lock: IctrlCommodityLock = IctrlCommodityLock.NONE,
) {
    constructor(commodity: IctrlCommodity) : this(commodity.id)
}
