package ru.otus.otuskotlin.incomingControl.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.stubs.IctrlStubs
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CommodityDeleteStubTest {
    private val processor = IctrlCommodityProcessor()
    private val id = IctrlCommodityId("666")

    @Test
    fun delete() = runTest {

        val ctx = IctrlContext(
            command = IctrlCommand.DELETE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.SUCCESS,
            commodityRequest = IctrlCommodity(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = IctrlCommodityStub.get()
        assertEquals(stub.id, ctx.commodityResponse.id)
        assertEquals(stub.name, ctx.commodityResponse.name)
        assertEquals(stub.description, ctx.commodityResponse.description)
        assertEquals(stub.commodityType, ctx.commodityResponse.commodityType)
        assertEquals(stub.manufacturer, ctx.commodityResponse.manufacturer)
        assertEquals(stub.receiptQuantity, ctx.commodityResponse.receiptQuantity)
        assertEquals(stub.visibility, ctx.commodityResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.DELETE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.BAD_ID,
            commodityRequest = IctrlCommodity(),
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.DELETE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.DB_ERROR,
            commodityRequest = IctrlCommodity(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.DELETE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.BAD_NAME,
            commodityRequest = IctrlCommodity(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}