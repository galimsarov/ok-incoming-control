package ru.otus.otuskotlin.incomingControl.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = IctrlCommodityStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationManufacturerCorrect(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
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
            lock = IctrlCommodityLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(IctrlState.FAILING, ctx.state)
    assertEquals("def", ctx.commodityValidated.manufacturer)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationManufacturerTrim(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = stub.id,
            name = "abc",
            description = "abc",
            manufacturer = " \n\t def \t\n ",
            receiptQuantity = "100",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
            lock = IctrlCommodityLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(IctrlState.FAILING, ctx.state)
    assertEquals("def", ctx.commodityValidated.manufacturer)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationManufacturerEmpty(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = stub.id,
            name = "abc",
            description = "abc",
            manufacturer = "",
            receiptQuantity = "100",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
            lock = IctrlCommodityLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("manufacturer", error?.field)
    assertContains(error?.message ?: "", "manufacturer")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationManufacturerSymbols(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("123"),
            name = "abc",
            description = "abc",
            manufacturer = "!@#$%^&*(),.{}",
            receiptQuantity = "100",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
            lock = IctrlCommodityLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("manufacturer", error?.field)
    assertContains(error?.message ?: "", "manufacturer")
}