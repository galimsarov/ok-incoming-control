package ru.otus.otuskotlin.incomingControl.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.incomingControl.IctrlAppSettings
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoInMemory
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoStub
import ru.otus.otuskotlin.incomingControl.repo.sql.CommodityRepoSQL
import ru.otus.otuskotlin.incomingControl.repo.sql.SqlProperties

fun Application.initAppSettings(sqlProperties: SqlProperties): IctrlAppSettings {
    val corSettings = IctrlCorSettings(
        repoTest = CommodityRepoInMemory(),
        repoProd = CommodityRepoSQL(sqlProperties),
        repoStub = CommodityRepoStub(),
    )
    return IctrlAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = IctrlCommodityProcessor(corSettings),
    )
}