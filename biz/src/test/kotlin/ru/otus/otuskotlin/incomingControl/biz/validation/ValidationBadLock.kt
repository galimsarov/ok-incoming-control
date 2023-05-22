package ru.otus.otuskotlin.incomingControl.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockCorrect(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            manufacturer = "abc",
            receiptQuantity = "123",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
            lock = IctrlCommodityLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(IctrlState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockTrim(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            manufacturer = "abc",
            receiptQuantity = "123",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
            lock = IctrlCommodityLock(" \n\t 123-234-abc-ABC \n\t "),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(IctrlState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockEmpty(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            manufacturer = "abc",
            receiptQuantity = "123",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
            lock = IctrlCommodityLock(""),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockFormat(command: IctrlCommand, processor: IctrlCommodityProcessor) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            manufacturer = "abc",
            receiptQuantity = "123",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_PUBLIC,
            lock = IctrlCommodityLock("!@#\$%^&*(),.{}"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(IctrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}