package ru.otus.otuskotlin.incomingControl.helpers

import ru.otus.otuskotlin.incomingControl.IctrlAppSettings
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoInMemory
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoStub

fun testSettings(repo: ICommodityRepository? = null) = IctrlAppSettings(
    corSettings = IctrlCorSettings(
        repoStub = CommodityRepoStub(),
        repoTest = repo ?: CommodityRepoInMemory(),
        repoProd = repo ?: CommodityRepoInMemory(),
    )
)