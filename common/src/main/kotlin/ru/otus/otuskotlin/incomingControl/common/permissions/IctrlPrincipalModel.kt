package ru.otus.otuskotlin.incomingControl.common.permissions

import ru.otus.otuskotlin.incomingControl.common.models.IctrlUserId

data class IctrlPrincipalModel(
    val id: IctrlUserId = IctrlUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<IctrlUserGroups> = emptySet()
) {
    companion object {
        val NONE = IctrlPrincipalModel()
    }
}
