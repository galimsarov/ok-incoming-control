package ru.otus.otuskotlin.incomingControl.auth

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlPrincipalRelations
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlUserPermissions

fun checkPermitted(
    command: IctrlCommand,
    relations: Iterable<IctrlPrincipalRelations>,
    permissions: Iterable<IctrlUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: IctrlCommand,
    val permission: IctrlUserPermissions,
    val relation: IctrlPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = IctrlCommand.CREATE,
        permission = IctrlUserPermissions.CREATE_OWN,
        relation = IctrlPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = IctrlCommand.READ,
        permission = IctrlUserPermissions.READ_OWN,
        relation = IctrlPrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = IctrlCommand.READ,
        permission = IctrlUserPermissions.READ_PUBLIC,
        relation = IctrlPrincipalRelations.PUBLIC,
    ) to true,

    // Update
    AccessTableConditions(
        command = IctrlCommand.UPDATE,
        permission = IctrlUserPermissions.UPDATE_OWN,
        relation = IctrlPrincipalRelations.OWN,
    ) to true,

    // Delete
    AccessTableConditions(
        command = IctrlCommand.DELETE,
        permission = IctrlUserPermissions.DELETE_OWN,
        relation = IctrlPrincipalRelations.OWN,
    ) to true,
)