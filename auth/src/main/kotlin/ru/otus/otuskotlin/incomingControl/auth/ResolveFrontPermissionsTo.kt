package ru.otus.otuskotlin.incomingControl.auth

import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlCommodityPermissionClient
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlPrincipalRelations
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<IctrlUserPermissions>,
    relations: Iterable<IctrlPrincipalRelations>,
) = mutableSetOf<IctrlCommodityPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    IctrlUserPermissions.READ_OWN to mapOf(IctrlPrincipalRelations.OWN to IctrlCommodityPermissionClient.READ),
    IctrlUserPermissions.READ_GROUP to mapOf(IctrlPrincipalRelations.GROUP to IctrlCommodityPermissionClient.READ),
    IctrlUserPermissions.READ_PUBLIC to mapOf(IctrlPrincipalRelations.PUBLIC to IctrlCommodityPermissionClient.READ),
    IctrlUserPermissions.READ_CANDIDATE to mapOf(IctrlPrincipalRelations.MODERATABLE to IctrlCommodityPermissionClient.READ),

    // UPDATE
    IctrlUserPermissions.UPDATE_OWN to mapOf(IctrlPrincipalRelations.OWN to IctrlCommodityPermissionClient.UPDATE),
    IctrlUserPermissions.UPDATE_PUBLIC to mapOf(IctrlPrincipalRelations.MODERATABLE to IctrlCommodityPermissionClient.UPDATE),
    IctrlUserPermissions.UPDATE_CANDIDATE to mapOf(IctrlPrincipalRelations.MODERATABLE to IctrlCommodityPermissionClient.UPDATE),

    // DELETE
    IctrlUserPermissions.DELETE_OWN to mapOf(IctrlPrincipalRelations.OWN to IctrlCommodityPermissionClient.DELETE),
    IctrlUserPermissions.DELETE_PUBLIC to mapOf(IctrlPrincipalRelations.MODERATABLE to IctrlCommodityPermissionClient.DELETE),
    IctrlUserPermissions.DELETE_CANDIDATE to mapOf(IctrlPrincipalRelations.MODERATABLE to IctrlCommodityPermissionClient.DELETE),
)