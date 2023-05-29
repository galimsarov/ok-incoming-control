package ru.otus.otuskotlin.incomingControl.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.incomingControl.api.v1.models.*
import ru.otus.otuskotlin.incomingControl.auth.addAuth
import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig
import ru.otus.otuskotlin.incomingControl.helpers.testSettings
import ru.otus.otuskotlin.incomingControl.module
import kotlin.test.assertEquals

class V1CommodityStubApiTest {
    @Test
    fun create() = testApplication {
        application { module(testSettings()) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/create") {
            val requestObj = CommodityCreateRequest(
                requestId = "12345",
                commodity = CommodityCreateObject(
                    name = "Болт",
                    description = "КРУТЕЙШИЙ",
                    manufacturer = "Болтозавод №1",
                    receiptQuantity = "10",
                    commodityType = CommodityType.FASTENER_PART,
                    visibility = CommodityVisibility.PUBLIC,
                ),
                debug = CommodityDebug(
                    mode = CommodityRequestDebugMode.STUB,
                    stub = CommodityRequestDebugStubs.SUCCESS
                )
            )
            addAuth(config = KtorAuthConfig.TEST, groups = listOf("USER"))
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityCreateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.commodity?.id)
    }

    @Test
    fun read() = testApplication {
        application { module(testSettings()) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/read") {
            val requestObj = CommodityReadRequest(
                requestId = "12345",
                commodity = CommodityReadObject("123"),
                debug = CommodityDebug(
                    mode = CommodityRequestDebugMode.STUB,
                    stub = CommodityRequestDebugStubs.SUCCESS
                )
            )
            addAuth(config = KtorAuthConfig.TEST, groups = listOf("USER"))
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.commodity?.id)
    }

    @Test
    fun update() = testApplication {
        application { module(testSettings()) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/update") {
            val requestObj = CommodityUpdateRequest(
                requestId = "12345",
                commodity = CommodityUpdateObject(
                    id = "123",
                    name = "Болт",
                    description = "КРУТЕЙШИЙ",
                    manufacturer = "Болтозавод №1",
                    receiptQuantity = "10",
                    commodityType = CommodityType.FASTENER_PART,
                    visibility = CommodityVisibility.PUBLIC,
                ),
                debug = CommodityDebug(
                    mode = CommodityRequestDebugMode.STUB,
                    stub = CommodityRequestDebugStubs.SUCCESS
                )
            )
            addAuth(config = KtorAuthConfig.TEST, groups = listOf("USER"))
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.commodity?.id)
    }

    @Test
    fun delete() = testApplication {
        application { module(testSettings()) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/delete") {
            val requestObj = CommodityDeleteRequest(
                requestId = "12345",
                commodity = CommodityDeleteObject(id = "123"),
                debug = CommodityDebug(
                    mode = CommodityRequestDebugMode.STUB,
                    stub = CommodityRequestDebugStubs.SUCCESS
                )
            )
            addAuth(config = KtorAuthConfig.TEST, groups = listOf("USER"))
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("123", responseObj.commodity?.id)
    }

    @Test
    fun search() = testApplication {
        application { module(testSettings()) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/search") {
            val requestObj = CommoditySearchRequest(
                requestId = "12345",
                commodityFilter = CommoditySearchFilter(),
                debug = CommodityDebug(
                    mode = CommodityRequestDebugMode.STUB,
                    stub = CommodityRequestDebugStubs.SUCCESS
                )
            )
            addAuth(config = KtorAuthConfig.TEST, groups = listOf("USER"))
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommoditySearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.commodities?.first()?.id)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}