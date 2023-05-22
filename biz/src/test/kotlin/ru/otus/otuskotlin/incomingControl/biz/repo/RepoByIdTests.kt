package ru.otus.otuskotlin.incomingControl.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityResponse
import ru.otus.otuskotlin.incomingControl.repo.tests.CommodityRepositoryMock
import kotlin.test.assertEquals

private val initCommodity = IctrlCommodity(
    id = IctrlCommodityId("123"),
    name = "abc",
    description = "abc",
    manufacturer = "abc",
    receiptQuantity = "123",
    commodityType = IctrlCommodityType.FASTENER_PART,
    visibility = IctrlVisibility.VISIBLE_PUBLIC,
)
private val repo = CommodityRepositoryMock(
    invokeReadCommodity = {
        if (it.id == initCommodity.id) {
            DbCommodityResponse(
                isSuccess = true,
                data = initCommodity,
            )
        } else DbCommodityResponse(
            isSuccess = false,
            data = null,
            errors = listOf(IctrlError(message = "Not found", field = "id"))
        )
    }
)
private val settings by lazy { IctrlCorSettings(repoTest = repo) }
private val processor by lazy { IctrlCommodityProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: IctrlCommand) = runTest {
    val ctx = IctrlContext(
        command = command,
        state = IctrlState.NONE,
        workMode = IctrlWorkMode.TEST,
        commodityRequest = IctrlCommodity(
            id = IctrlCommodityId("12345"),
            name = "xyz",
            description = "xyz",
            manufacturer = "xyz",
            receiptQuantity = "234",
            commodityType = IctrlCommodityType.TUBE_LINE_PART,
            visibility = IctrlVisibility.VISIBLE_TO_GROUP,
            lock = IctrlCommodityLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(IctrlState.FAILING, ctx.state)
    assertEquals(IctrlCommodity(), ctx.commodityResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}