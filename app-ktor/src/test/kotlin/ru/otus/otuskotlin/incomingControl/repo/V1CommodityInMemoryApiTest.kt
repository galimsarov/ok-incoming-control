package ru.otus.otuskotlin.incomingControl.repo

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
import ru.otus.otuskotlin.incomingControl.IctrlAppSettings
import ru.otus.otuskotlin.incomingControl.api.v1.models.*
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityLock
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityType
import ru.otus.otuskotlin.incomingControl.common.models.IctrlVisibility
import ru.otus.otuskotlin.incomingControl.module
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoInMemory
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class V1CommodityInMemoryApiTest {
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val initCommodity = IctrlCommodityStub.prepareResult {
        id = IctrlCommodityId(uuidOld)
        name = "abc"
        description = "abc"
        manufacturer = "abc"
        receiptQuantity = "123"
        commodityType = IctrlCommodityType.FASTENER_PART
        visibility = IctrlVisibility.VISIBLE_PUBLIC
        lock = IctrlCommodityLock(uuidOld)
    }

    @Test
    fun create() = testApplication {
        val repo = CommodityRepoInMemory(initObjects = listOf(initCommodity), randomUuid = { uuidNew })
        application { module(IctrlAppSettings(corSettings = IctrlCorSettings(repoTest = repo))) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val createCommodity = CommodityCreateObject(
            name = "Болт",
            description = "КРУТЕЙШИЙ",
            manufacturer = "Завод",
            receiptQuantity = "123",
            commodityType = CommodityType.FASTENER_PART,
            visibility = CommodityVisibility.PUBLIC,
        )

        val response = client.post("/v1/commodity/create") {
            val requestObj = CommodityCreateRequest(
                requestId = "12345",
                commodity = createCommodity,
                debug = CommodityDebug(mode = CommodityRequestDebugMode.TEST)
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidNew, responseObj.commodity?.id)
        assertEquals(createCommodity.name, responseObj.commodity?.name)
        assertEquals(createCommodity.description, responseObj.commodity?.description)
        assertEquals(createCommodity.manufacturer, responseObj.commodity?.manufacturer)
        assertEquals(createCommodity.receiptQuantity, responseObj.commodity?.receiptQuantity)
        assertEquals(createCommodity.commodityType, responseObj.commodity?.commodityType)
        assertEquals(createCommodity.visibility, responseObj.commodity?.visibility)
    }

    @Test
    fun read() = testApplication {
        val repo = CommodityRepoInMemory(initObjects = listOf(initCommodity), randomUuid = { uuidNew })
        application { module(IctrlAppSettings(corSettings = IctrlCorSettings(repoTest = repo))) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/read") {
            val requestObj = CommodityReadRequest(
                requestId = "12345",
                commodity = CommodityReadObject(uuidOld),
                debug = CommodityDebug(mode = CommodityRequestDebugMode.TEST)
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.commodity?.id)
    }

    @Test
    fun update() = testApplication {
        val repo = CommodityRepoInMemory(initObjects = listOf(initCommodity), randomUuid = { uuidNew })
        application { module(IctrlAppSettings(corSettings = IctrlCorSettings(repoTest = repo))) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val commodityUpdate = CommodityUpdateObject(
            id = uuidOld,
            name = "Болт",
            description = "КРУТЕЙШИЙ",
            manufacturer = "Завод",
            receiptQuantity = "123",
            commodityType = CommodityType.FASTENER_PART,
            visibility = CommodityVisibility.PUBLIC,
            lock = initCommodity.lock.asString(),
        )

        val response = client.post("/v1/commodity/update") {
            val requestObj = CommodityUpdateRequest(
                requestId = "12345",
                commodity = commodityUpdate,
                debug = CommodityDebug(mode = CommodityRequestDebugMode.TEST)
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(commodityUpdate.id, responseObj.commodity?.id)
        assertEquals(commodityUpdate.name, responseObj.commodity?.name)
        assertEquals(commodityUpdate.description, responseObj.commodity?.description)
        assertEquals(commodityUpdate.manufacturer, responseObj.commodity?.manufacturer)
        assertEquals(commodityUpdate.receiptQuantity, responseObj.commodity?.receiptQuantity)
        assertEquals(commodityUpdate.commodityType, responseObj.commodity?.commodityType)
        assertEquals(commodityUpdate.visibility, responseObj.commodity?.visibility)
    }

    @Test
    fun delete() = testApplication {
        val repo = CommodityRepoInMemory(initObjects = listOf(initCommodity), randomUuid = { uuidNew })
        application { module(IctrlAppSettings(corSettings = IctrlCorSettings(repoTest = repo))) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/delete") {
            val requestObj = CommodityDeleteRequest(
                requestId = "12345",
                commodity = CommodityDeleteObject(id = uuidOld, lock = initCommodity.lock.asString()),
                debug = CommodityDebug(mode = CommodityRequestDebugMode.TEST)
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommodityDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.commodity?.id)
    }

    @Test
    fun search() = testApplication {
        val repo = CommodityRepoInMemory(initObjects = listOf(initCommodity), randomUuid = { uuidNew })
        application { module(IctrlAppSettings(corSettings = IctrlCorSettings(repoTest = repo))) }
        environment { config = MapApplicationConfig() }
        val client = myClient()

        val response = client.post("/v1/commodity/search") {
            val requestObj = CommoditySearchRequest(
                requestId = "12345",
                commodityFilter = CommoditySearchFilter(),
                debug = CommodityDebug(mode = CommodityRequestDebugMode.TEST)
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CommoditySearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.commodities?.size)
        assertEquals(uuidOld, responseObj.commodities?.first()?.id)
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