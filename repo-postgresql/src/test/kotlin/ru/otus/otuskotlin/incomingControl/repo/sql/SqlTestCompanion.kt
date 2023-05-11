package ru.otus.otuskotlin.incomingControl.repo.sql

import org.testcontainers.containers.PostgreSQLContainer
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import java.time.Duration
import java.util.*

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "incomingcontrol-pass"
    private const val SCHEMA = "incomingcontrol"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: Collection<IctrlCommodity> = emptyList(),
        randomUuid: () -> String = { UUID.randomUUID().toString() },
    ): CommodityRepoSQL {
        return CommodityRepoSQL(
            SqlProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjects.toList(),
            randomUuid = randomUuid
        )
    }
}