package ru.otus.otuskotlin.incomingControl.repo.inmemory

import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.repo.tests.RepoCommodityUpdateTest

class CommodityRepoInMemoryUpdateTest : RepoCommodityUpdateTest() {
    override val repo: ICommodityRepository =
        CommodityRepoInMemory(initObjects = initObjects, randomUuid = { lockNew.asString() })
}