package ru.otus.otuskotlin.incomingControl.common.models

data class IctrlCommodityFilter(
    var searchString: String = "",
    var ownerId: IctrlUserId = IctrlUserId.NONE,
    var searchPermissions: MutableSet<IctrlSearchPermissions> = mutableSetOf(),
)