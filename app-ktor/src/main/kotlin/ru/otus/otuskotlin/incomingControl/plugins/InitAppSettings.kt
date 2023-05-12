package ru.otus.otuskotlin.incomingControl.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.incomingControl.IctrlAppSettings
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoStub

fun Application.initAppSettings(): IctrlAppSettings {
    val corSettings = IctrlCorSettings(
        repoTest = getDatabaseConf(CommodityDbType.TEST),
        repoProd = getDatabaseConf(CommodityDbType.PROD),
        repoStub = CommodityRepoStub(),
    )
    return IctrlAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = IctrlCommodityProcessor(corSettings),
    )
}