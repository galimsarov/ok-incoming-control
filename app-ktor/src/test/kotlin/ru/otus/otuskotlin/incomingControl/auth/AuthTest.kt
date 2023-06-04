package ru.otus.otuskotlin.incomingControl.auth

import io.ktor.client.request.*
import io.ktor.server.config.*
import io.ktor.server.testing.*

import org.junit.Test
import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig
import ru.otus.otuskotlin.incomingControl.helpers.testSettings
import ru.otus.otuskotlin.incomingControl.module
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun invalidAudience() = testApplication {
        application { module(testSettings()) }
        environment { config = MapApplicationConfig() }

        val response = client.post("/v1/commodity/create") {
            addAuth(config = KtorAuthConfig.TEST.copy(audience = "invalid"))
        }
        assertEquals(401, response.status.value)
    }
}