package ru.otus.otuskotlin.incomingControl.biz

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            manufacturer = "def",
            receiptQuantity = "100",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(IctrlState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId(" \n\t 123-234-abc-ABC \n\t "),
            name = "abc",
            description = "abc",
            manufacturer = "def",
            receiptQuantity = "100",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(IctrlState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId(""),
            name = "abc",
            description = "abc",
            manufacturer = "def",
            receiptQuantity = "100",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("!@#\$%^&*(),.{}"),
            name = "abc",
            description = "abc",
            manufacturer = "def",
            receiptQuantity = "100",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}