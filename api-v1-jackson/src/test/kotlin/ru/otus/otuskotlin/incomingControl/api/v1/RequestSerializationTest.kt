package ru.otus.otuskotlin.incomingControl.api.v1

import ru.otus.otuskotlin.incomingControl.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = CommodityCreateRequest(
        requestId = "123",
        debug = CommodityDebug(
            mode = CommodityRequestDebugMode.STUB,
            stub = CommodityRequestDebugStubs.BAD_NAME
        ),
        commodity = CommodityCreateObject(
            name = "commodity name",
            description = "commodity description",
            manufacturer = "ABC Inc",
            receiptQuantity = "12",
            commodityType = CommodityType.TUBE_LINE_PART,
            visibility = CommodityVisibility.PUBLIC
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"requestId\":\\s*\"123\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"name\":\\s*\"commodity name\""))
        assertContains(json, Regex("\"description\":\\s*\"commodity description\""))
        assertContains(json, Regex("\"manufacturer\":\\s*\"ABC Inc\""))
        assertContains(json, Regex("\"receiptQuantity\":\\s*\"12\""))
        assertContains(json, Regex("\"commodityType\":\\s*\"tubeLinePart\""))
        assertContains(json, Regex("\"visibility\":\\s*\"public\""))

    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as CommodityCreateRequest

        assertEquals(request, obj)
    }
}