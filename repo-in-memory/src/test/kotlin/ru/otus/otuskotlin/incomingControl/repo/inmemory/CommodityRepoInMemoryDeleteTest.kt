package ru.otus.otuskotlin.incomingControl.repo.inmemory

import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.repo.tests.RepoCommodityDeleteTest

class CommodityRepoInMemoryDeleteTest : RepoCommodityDeleteTest() {
    override val repo: ICommodityRepository = CommodityRepoInMemory(initObjects = initObjects)
}