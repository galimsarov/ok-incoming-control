package ru.otus.otuskotlin.incomingControl.biz.general

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.errorAdministration
import ru.otus.otuskotlin.incomingControl.common.helpers.fail
import ru.otus.otuskotlin.incomingControl.common.models.IctrlWorkMode
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.cor.ICorChainDsl
import ru.otus.otuskotlin.incomingControl.cor.worker

fun ICorChainDsl<IctrlContext>.initRepo(title: String) = worker {
    this.title = title
    description = "Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы"
    handle {
        commodityRepo = when (workMode) {
            IctrlWorkMode.TEST -> settings.repoTest
            IctrlWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != IctrlWorkMode.STUB && commodityRepo == ICommodityRepository.NONE)
            fail(
                errorAdministration(
                    field = "repo",
                    violationCode = "dbNotConfigured",
                    description = "The database is unconfigured for chosen workmode ($workMode). " +
                            "Please, contact the administrator staff"
                )
            )
    }
}