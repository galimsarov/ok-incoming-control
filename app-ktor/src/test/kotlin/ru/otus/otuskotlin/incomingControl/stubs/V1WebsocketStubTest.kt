package ru.otus.otuskotlin.incomingControl.stubs

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1Mapper
import ru.otus.otuskotlin.incomingControl.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1WebsocketStubTest {
    @Test
    fun createStub() {
        val request = CommodityCreateRequest(
            requestId = "123",
            commodity = CommodityCreateObject(
                name = "Bolt",
                description = "The best",
                manufacturer = "OCP",
                receiptQuantity = "10",
                commodityType = CommodityType.FASTENER_PART,
                visibility = CommodityVisibility.PUBLIC
            ),
            debug = CommodityDebug(
                mode = CommodityRequestDebugMode.STUB,
                stub = CommodityRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun readStub() {
        val request = CommodityReadRequest(
            requestId = "123",
            commodity = CommodityReadObject("666"),
            debug = CommodityDebug(
                mode = CommodityRequestDebugMode.STUB,
                stub = CommodityRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun updateStub() {
        val request = CommodityUpdateRequest(
            requestId = "123",
            commodity = CommodityUpdateObject(
                id = "666",
                name = "Bolt",
                description = "The best",
                manufacturer = "OCP",
                receiptQuantity = "10",
                commodityType = CommodityType.FASTENER_PART,
                visibility = CommodityVisibility.PUBLIC
            ),
            debug = CommodityDebug(
                mode = CommodityRequestDebugMode.STUB,
                stub = CommodityRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun deleteStub() {
        val request = CommodityDeleteRequest(
            requestId = "123",
            commodity = CommodityDeleteObject("666"),
            debug = CommodityDebug(
                mode = CommodityRequestDebugMode.STUB,
                stub = CommodityRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun searchStub() {
        val request = CommoditySearchRequest(
            requestId = "123",
            commodityFilter = CommoditySearchFilter(),
            debug = CommodityDebug(
                mode = CommodityRequestDebugMode.STUB,
                stub = CommodityRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    private inline fun <reified T> testMethod(
        request: Any,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/ws/v1") {
            withTimeout(3000) {
                val income = incoming.receive() as Frame.Text
                val response = apiV1Mapper.readValue(income.readText(), T::class.java)
                assertIs<CommodityInitResponse>(response)
            }
            send(Frame.Text(apiV1Mapper.writeValueAsString(request)))
            withTimeout(3000) {
                val income = incoming.receive() as Frame.Text
                val response = apiV1Mapper.readValue(income.readText(), T::class.java)

                assertBlock(response)
            }
        }
    }
}