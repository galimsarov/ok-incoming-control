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
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class CommoditySearchStubTest {
    private val processor = IctrlCommodityProcessor()
    private val filter = IctrlCommodityFilter(searchString = "bolt")

    @Test
    fun read() = runTest {

        val ctx = IctrlContext(
            command = IctrlCommand.SEARCH,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.SUCCESS,
            commodityFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.commoditiesResponse.size > 1)
        val first = ctx.commoditiesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.name.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with(IctrlCommodityStub.get()) {
            assertEquals(commodityType, first.commodityType)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.SEARCH,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.BAD_ID,
            commodityFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.SEARCH,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.DB_ERROR,
            commodityFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = IctrlContext(
            command = IctrlCommand.SEARCH,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.STUB,
            stubCase = IctrlStubs.BAD_NAME,
            commodityFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(IctrlCommodity(), ctx.commodityResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}