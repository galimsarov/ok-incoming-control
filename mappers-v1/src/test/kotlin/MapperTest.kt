import org.junit.Test
import ru.otus.otuskotlin.api.v1.models.*

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
//        context.fromTransport(req)
//
//        assertEquals(MkplStubs.SUCCESS, context.stubCase)
//        assertEquals(MkplWorkMode.STUB, context.workMode)
//        assertEquals("title", context.adRequest.title)
//        assertEquals(MkplVisibility.VISIBLE_PUBLIC, context.adRequest.visibility)
//        assertEquals(MkplDealSide.DEMAND, context.adRequest.adType)
    }
}