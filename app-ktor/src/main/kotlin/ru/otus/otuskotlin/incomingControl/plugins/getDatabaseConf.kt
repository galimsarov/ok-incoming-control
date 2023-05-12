package ru.otus.otuskotlin.incomingControl.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoInMemory
import ru.otus.otuskotlin.incomingControl.repo.sql.CommodityRepoSQL
import ru.otus.otuskotlin.incomingControl.repo.sql.SqlProperties

fun Application.getDatabaseConf(type: CommodityDbType): ICommodityRepository = when (type) {
    CommodityDbType.PROD -> CommodityRepoSQL(environment.getSqlProperties())
    CommodityDbType.TEST -> CommodityRepoInMemory()
}

enum class CommodityDbType { PROD, TEST }

private fun ApplicationEnvironment.getSqlProperties() = SqlProperties(
    url = config.property("sql.url").getString(),
    user = config.property("sql.user").getString(),
    password = config.property("sql.password").getString(),
    schema = config.property("sql.schema").getString(),
    dropDatabase = config.property("sql.drop-database").getString().toBoolean(),
)