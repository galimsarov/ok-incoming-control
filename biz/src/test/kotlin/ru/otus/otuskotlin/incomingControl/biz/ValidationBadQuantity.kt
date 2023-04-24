package ru.otus.otuskotlin.incomingControl.biz

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = IctrlCommodityStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationQuantityCorrect(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = stub.id,
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
    assertEquals("100", ctx.commodityValidated.receiptQuantity)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationQuantityTrim(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = stub.id,
            name = "abc",
            description = "abc",
            manufacturer = "def",
            receiptQuantity = " \n\t 100 \t\n ",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(IctrlState.FAILING, ctx.state)
    assertEquals("100", ctx.commodityValidated.receiptQuantity)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationQuantityEmpty(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = stub.id,
            name = "abc",
            description = "abc",
            manufacturer = "def",
            receiptQuantity = "",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("receiptQuantity", error?.field)
    assertContains(error?.message ?: "", "receiptQuantity")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationQuantitySymbols(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("123"),
            name = "abc",
            description = "abc",
            manufacturer = "def",
            receiptQuantity = "One hundred",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("receiptQuantity", error?.field)
    assertContains(error?.message ?: "", "receiptQuantity")
}