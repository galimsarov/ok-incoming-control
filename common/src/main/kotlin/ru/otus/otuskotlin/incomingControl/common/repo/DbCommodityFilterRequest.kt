package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.models.IctrlUserId

data class DbCommodityFilterRequest(
    val nameFilter: String = "",
    val ownerId: IctrlUserId = IctrlUserId.NONE
)
