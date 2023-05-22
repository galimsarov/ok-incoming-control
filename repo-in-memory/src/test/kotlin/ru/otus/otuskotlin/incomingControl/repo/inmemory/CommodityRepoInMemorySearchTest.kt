package ru.otus.otuskotlin.incomingControl.repo.inmemory

import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.repo.tests.RepoCommoditySearchTest

class CommodityRepoInMemorySearchTest : RepoCommoditySearchTest() {
    override val repo: ICommodityRepository = CommodityRepoInMemory(initObjects = initObjects)
}