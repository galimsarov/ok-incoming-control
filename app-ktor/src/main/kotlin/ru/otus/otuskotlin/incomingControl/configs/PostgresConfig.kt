package ru.otus.otuskotlin.incomingControl.configs

import io.ktor.server.config.*

data class PostgresConfig(
    val url: String = "jdbc:postgresql://localhost:5432/incomingcontrol",
    val user: String = "postgres",
    val password: String = "incomingcontrol-pass",
    val schema: String = "incomingcontrol",
) {
    constructor(config: ApplicationConfig) : this(
        url = config.property("$PATH.url").getString(),
        user = config.property("$PATH.user").getString(),
        password = config.property("$PATH.password").getString(),
        schema = config.property("$PATH.schema").getString(),
    )

    companion object {
        const val PATH = "${ConfigPaths.repository}.psql"
    }
}