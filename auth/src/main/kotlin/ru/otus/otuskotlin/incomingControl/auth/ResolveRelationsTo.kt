package ru.otus.otuskotlin.incomingControl.auth

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.common.models.IctrlVisibility
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlPrincipalModel
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlPrincipalRelations

fun IctrlCommodity.resolveRelationsTo(principal: IctrlPrincipalModel): Set<IctrlPrincipalRelations> = setOfNotNull(
    IctrlPrincipalRelations.NONE,
    IctrlPrincipalRelations.NEW.takeIf { id == IctrlCommodityId.NONE },
    IctrlPrincipalRelations.OWN.takeIf { principal.id == ownerId },
    IctrlPrincipalRelations.MODERATABLE.takeIf { visibility != IctrlVisibility.VISIBLE_TO_OWNER },
    IctrlPrincipalRelations.PUBLIC.takeIf { visibility == IctrlVisibility.VISIBLE_PUBLIC },
)