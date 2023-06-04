package ru.otus.otuskotlin.incomingControl.biz.permissions

import ru.otus.otuskotlin.incomingControl.auth.checkPermitted
import ru.otus.otuskotlin.incomingControl.auth.resolveRelationsTo
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.chain
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == IctrlState.RUNNING }
    worker("Вычисление отношения материала к принципалу") {
        commodityRepoRead.principalRelations = commodityRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к материалу") {
        permitted = checkPermitted(command, commodityRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(IctrlError(message = "User is not allowed to perform this operation"))
        }
    }
}
