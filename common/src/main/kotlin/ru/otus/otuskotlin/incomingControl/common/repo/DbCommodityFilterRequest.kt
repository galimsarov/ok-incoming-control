package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.models.IctrlUserId

data class DbCommodityFilterRequest(
    val titleFilter: String = "",
    val ownerId: IctrlUserId = IctrlUserId.NONE
)
