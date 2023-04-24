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
class CommodityReadStubTest {
    private val processor = IctrlCommodityProcessor()
    private val id = IctrlCommodityId("666")

    @Test
    fun read() = runTest {

        val ctx = IctrlContext(
            command = IctrlCommand.READ,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.SUCCESS,
            commodityRequest = IctrlCommodity(
                id = id,
            ),
        )
        processor.exec(ctx)
        with(IctrlCommodityStub.get()) {
            assertEquals(id, ctx.commodityResponse.id)
            assertEquals(name, ctx.commodityResponse.name)
            assertEquals(description, ctx.commodityResponse.description)
            assertEquals(commodityType, ctx.commodityResponse.commodityType)
            assertEquals(manufacturer, ctx.commodityResponse.manufacturer)
            assertEquals(receiptQuantity, ctx.commodityResponse.receiptQuantity)
            assertEquals(visibility, ctx.commodityResponse.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.READ,
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
            command = IctrlCommand.READ,
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
            command = IctrlCommand.READ,
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