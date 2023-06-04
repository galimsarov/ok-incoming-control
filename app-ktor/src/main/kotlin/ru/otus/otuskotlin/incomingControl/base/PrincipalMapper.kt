package ru.otus.otuskotlin.incomingControl.base

import io.ktor.server.auth.jwt.*
import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig.Companion.F_NAME_CLAIM
import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig.Companion.ID_CLAIM
import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig.Companion.L_NAME_CLAIM
import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig.Companion.M_NAME_CLAIM
import ru.otus.otuskotlin.incomingControl.common.models.IctrlUserId
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlPrincipalModel
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlUserGroups

fun JWTPrincipal?.toModel() = this?.run {
    IctrlPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { IctrlUserId(it) } ?: IctrlUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when (it) {
                    "USER" -> IctrlUserGroups.USER
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: IctrlPrincipalModel.NONE