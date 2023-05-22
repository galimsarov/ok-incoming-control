package ru.otus.otuskotlin.incomingControl.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityResponse
import ru.otus.otuskotlin.incomingControl.repo.tests.CommodityRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {
    private val userId = IctrlUserId("321")
    private val command = IctrlCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = CommodityRepositoryMock(
        invokeCreateCommodity = {
            DbCommodityResponse(
                isSuccess = true,
                data = IctrlCommodity(
                    id = IctrlCommodityId(uuid),
                    name = it.commodity.name,
                    description = it.commodity.description,
                    manufacturer = it.commodity.manufacturer,
                    receiptQuantity = it.commodity.receiptQuantity,
                    commodityType = it.commodity.commodityType,
                    ownerId = userId,
                    visibility = it.commodity.visibility,
                )
            )
        }
    )
    private val settings = IctrlCorSettings(repoTest = repo)
    private val processor = IctrlCommodityProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = IctrlContext(
            command = command,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.TEST,
            commodityRequest = IctrlCommodity(
                name = "abc",
                description = "abc",
                manufacturer = "abc",
                receiptQuantity = "123",
                commodityType = IctrlCommodityType.FASTENER_PART,
                visibility = IctrlVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(IctrlState.FINISHING, ctx.state)
        assertNotEquals(IctrlCommodityId.NONE, ctx.commodityResponse.id)
        assertEquals("abc", ctx.commodityResponse.name)
        assertEquals("abc", ctx.commodityResponse.description)
        assertEquals("abc", ctx.commodityResponse.manufacturer)
        assertEquals("123", ctx.commodityResponse.receiptQuantity)
        assertEquals(IctrlCommodityType.FASTENER_PART, ctx.commodityResponse.commodityType)
        assertEquals(IctrlVisibility.VISIBLE_PUBLIC, ctx.commodityResponse.visibility)
    }
}