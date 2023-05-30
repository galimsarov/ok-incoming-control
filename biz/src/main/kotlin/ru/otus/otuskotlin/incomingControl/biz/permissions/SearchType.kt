package ru.otus.otuskotlin.incomingControl.biz.permissions

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlSearchPermissions
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlUserPermissions
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.chain
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == IctrlState.RUNNING }
    worker("Определение типа поиска") {
        commodityFilterValidated.searchPermissions = setOfNotNull(
            IctrlSearchPermissions.OWN.takeIf { permissionsChain.contains(IctrlUserPermissions.SEARCH_OWN) },
            IctrlSearchPermissions.PUBLIC.takeIf { permissionsChain.contains(IctrlUserPermissions.SEARCH_PUBLIC) },
            IctrlSearchPermissions.REGISTERED.takeIf { permissionsChain.contains(IctrlUserPermissions.SEARCH_REGISTERED) },
        ).toMutableSet()
    }
}