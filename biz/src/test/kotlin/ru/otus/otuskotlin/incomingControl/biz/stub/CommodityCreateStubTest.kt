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
class CommodityCreateStubTest {

    private val processor = IctrlCommodityProcessor()
    private val id = IctrlCommodityId("666")
    private val name = "name 666"
    private val description = "desc 666"
    private val commodityType = IctrlCommodityType.FASTENER_PART
    private val manufacturer = "manufacturer 666"
    private val receiptQuantity = "666"
    private val visibility = IctrlVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.CREATE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.SUCCESS,
            commodityRequest = IctrlCommodity(
                id = id,
                name = name,
                description = description,
                commodityType = commodityType,
                manufacturer = manufacturer,
                receiptQuantity = receiptQuantity,
                visibility = visibility
            )
        )
        processor.exec(ctx)

        assertEquals(IctrlCommodityStub.get().id, ctx.commodityResponse.id)
        assertEquals(name, ctx.commodityResponse.name)
        assertEquals(description, ctx.commodityResponse.description)
        assertEquals(commodityType, ctx.commodityResponse.commodityType)
        assertEquals(manufacturer, ctx.commodityResponse.manufacturer)
        assertEquals(receiptQuantity, ctx.commodityResponse.receiptQuantity)
        assertEquals(visibility, ctx.commodityResponse.visibility)
    }

    @Test
    fun badName() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.CREATE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.BAD_NAME,
            commodityRequest = IctrlCommodity(
                id = id,
                name = "",
                description = description,
                commodityType = commodityType,
                manufacturer = manufacturer,
                receiptQuantity = receiptQuantity,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("name", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badDescription() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.CREATE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.BAD_DESCRIPTION,
            commodityRequest = IctrlCommodity(
                id = id,
                name = name,
                description = "",
                commodityType = commodityType,
                manufacturer = manufacturer,
                receiptQuantity = receiptQuantity,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.CREATE,
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
            command = IctrlCommand.CREATE,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.BAD_ID,
            commodityRequest = IctrlCommodity(
                id = id,
                name = name,
                description = description,
                commodityType = commodityType,
                manufacturer = manufacturer,
                receiptQuantity = receiptQuantity,
                visibility = visibility
            ),
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}