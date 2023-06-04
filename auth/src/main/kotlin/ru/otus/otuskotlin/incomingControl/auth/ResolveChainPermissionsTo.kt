package ru.otus.otuskotlin.incomingControl.auth

import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlUserGroups
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlUserPermissions

fun resolveChainPermissions(
    groups: Iterable<IctrlUserGroups>,
) = mutableSetOf<IctrlUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    IctrlUserGroups.USER to setOf(
        IctrlUserPermissions.READ_OWN,
        IctrlUserPermissions.READ_PUBLIC,
        IctrlUserPermissions.CREATE_OWN,
        IctrlUserPermissions.UPDATE_OWN,
        IctrlUserPermissions.DELETE_OWN,
    ),
    IctrlUserGroups.MODERATOR_IC to setOf(),
    IctrlUserGroups.ADMIN_COMMODITY to setOf(),
    IctrlUserGroups.TEST to setOf(),
    IctrlUserGroups.BAN_COMMODITY to setOf(),
)

private val groupPermissionsDenys = mapOf(
    IctrlUserGroups.USER to setOf(),
    IctrlUserGroups.MODERATOR_IC to setOf(),
    IctrlUserGroups.ADMIN_COMMODITY to setOf(),
    IctrlUserGroups.TEST to setOf(),
    IctrlUserGroups.BAN_COMMODITY to setOf(
        IctrlUserPermissions.UPDATE_OWN,
        IctrlUserPermissions.CREATE_OWN,
    ),
)