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
                manufacturer = "ABC Inc",
                receiptQuantity = "12",
                commodityType = CommodityType.TUBE_LINE_PART,
                visibility = CommodityVisibility.PUBLIC,
            ),
        )

        val context = IctrlContext()
        context.fromTransport(req)

        assertEquals("1234", context.requestId.asString())
        assertEquals(IctrlCommand.CREATE, context.command)
        assertEquals(IctrlWorkMode.STUB, context.workMode)
        assertEquals(IctrlStubs.SUCCESS, context.stubCase)
        assertEquals("name", context.commodityRequest.name)
        assertEquals("desc", context.commodityRequest.description)
        assertEquals("ABC Inc", context.commodityRequest.manufacturer)
        assertEquals("12", context.commodityRequest.receiptQuantity)
        assertEquals(IctrlCommodityType.TUBE_LINE_PART, context.commodityRequest.commodityType)
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
                manufacturer = "ABC Inc",
                receiptQuantity = "12",
                commodityType = IctrlCommodityType.TUBE_LINE_PART,
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
        assertEquals("ABC Inc", req.commodity?.manufacturer)
        assertEquals("12", req.commodity?.receiptQuantity)
        assertEquals(CommodityType.TUBE_LINE_PART, req.commodity?.commodityType)
        assertEquals(CommodityVisibility.PUBLIC, req.commodity?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("name", req.errors?.firstOrNull()?.field)
        assertEquals("wrong name", req.errors?.firstOrNull()?.message)
        assertEquals(ResponseResult.SUCCESS, req.result)
    }
}