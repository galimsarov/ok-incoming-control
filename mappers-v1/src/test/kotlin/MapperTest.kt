import models.IctrlVisibility
import models.IctrlWorkMode
import org.junit.Test
import ru.otus.otuskotlin.api.v1.models.*
import stubs.IctrlStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = CommodityCreateRequest(
            requestId = "1234",
            debug = CommodityDebug(
                mode = CommodityRequestDebugMode.STUB,
                stub = CommodityRequestDebugStubs.SUCCESS,
            ),
            commodity = CommodityCreateObject(
                name = "name",
                description = "desc",
                receiptQuantity = "12",
                visibility = CommodityVisibility.PUBLIC,
            ),
        )

        val context = IctrlContext()
        context.fromTransport(req)

        assertEquals(IctrlStubs.SUCCESS, context.stubCase)
        assertEquals(IctrlWorkMode.STUB, context.workMode)
        assertEquals("name", context.commodityRequest.name)
        assertEquals(IctrlVisibility.VISIBLE_PUBLIC, context.commodityRequest.visibility)
    }
}