package ru.otus.otuskotlin.incomingControl.plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import ru.otus.otuskotlin.incomingControl.configs.ConfigPaths
import ru.otus.otuskotlin.incomingControl.configs.PostgresConfig
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoInMemory
import ru.otus.otuskotlin.incomingControl.repo.sql.CommodityRepoSQL
import ru.otus.otuskotlin.incomingControl.repo.sql.SqlProperties
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConf(type: CommodityDbType): ICommodityRepository {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.conf to one of: 'inmemory', 'postgres'"
        )
    }
}

enum class CommodityDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

private fun Application.initInMemory(): ICommodityRepository {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return CommodityRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}

private fun Application.initPostgres(): ICommodityRepository {
    val config = PostgresConfig(environment.config)
    return CommodityRepoSQL(
        properties = SqlProperties(
            url = config.url,
            user = config.user,
            password = config.password,
            schema = config.schema,
        )
    )
}