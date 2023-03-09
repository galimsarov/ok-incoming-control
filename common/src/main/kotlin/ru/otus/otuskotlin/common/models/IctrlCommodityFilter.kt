package ru.otus.otuskotlin.common.models

data class IctrlCommodityFilter(
    var searchString: String = "",
    var ownerId: IctrlUserId = IctrlUserId.NONE,
)