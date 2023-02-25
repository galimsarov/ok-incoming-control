import models.*
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
        assertEquals("desc", context.commodityRequest.description)
        assertEquals("12", context.commodityRequest.receiptQuantity)
        assertEquals(IctrlVisibility.VISIBLE_PUBLIC, context.commodityRequest.visibility)
    }

    @Test
    fun toTransport() {
        val context = IctrlContext(
            requestId = IctrlRequestId("1234"),
            command = IctrlCommand.CREATE,
            commodityResponse = IctrlCommodity(
                name = "name",
                description = "desc",
                receiptQuantity = "12",
                visibility = IctrlVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                IctrlError(
                    code = "err",
                    group = "request",
                    field = "name",
                    message = "wrong name",
                )
            ),
            state = IctrlState.RUNNING,
        )

        val req = context.toTransportCommodity() as CommodityCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("name", req.commodity?.name)
        assertEquals("desc", req.commodity?.description)
        assertEquals(CommodityVisibility.PUBLIC, req.commodity?.visibility)
        assertEquals("12", req.commodity?.receiptQuantity)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("name", req.errors?.firstOrNull()?.field)
        assertEquals("wrong name", req.errors?.firstOrNull()?.message)
    }
}