import ru.otus.otuskotlin.api.v1.models.*
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
            receiptQuantity = "12",
            visibility = CommodityVisibility.PUBLIC
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"name\":\\s*\"commodity name\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as CommodityCreateRequest

        assertEquals(request, obj)
    }
}