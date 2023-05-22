package ru.otus.otuskotlin.incomingControl.repo.sql

import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.repo.tests.*

class RepoCommoditySQLCreateTest : RepoCommodityCreateTest() {
    override val repo: ICommodityRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoCommoditySQLDeleteTest : RepoCommodityDeleteTest() {
    override val repo: ICommodityRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoCommoditySQLReadTest : RepoCommodityReadTest() {
    override val repo: ICommodityRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLSearchTest : RepoCommoditySearchTest() {
    override val repo: ICommodityRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoCommoditySQLUpdateTest : RepoCommodityUpdateTest() {
    override val repo: ICommodityRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}