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
import kotlin.test.assertTrue

class BizRepoDeleteTest {
    private val userId = IctrlUserId("321")
    private val command = IctrlCommand.DELETE
    private val initCommodity = IctrlCommodity(
        id = IctrlCommodityId("123"),
        name = "abc",
        description = "abc",
        manufacturer = "abc",
        receiptQuantity = "123",
        commodityType = IctrlCommodityType.FASTENER_PART,
        ownerId = userId,
        visibility = IctrlVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy {
        CommodityRepositoryMock(
            invokeReadCommodity = { DbCommodityResponse(isSuccess = true, data = initCommodity) },
            invokeDeleteCommodity = {
                if (it.id == initCommodity.id)
                    DbCommodityResponse(
                        isSuccess = true,
                        data = initCommodity
                    )
                else DbCommodityResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy { IctrlCorSettings(repoTest = repo) }
    private val processor by lazy { IctrlCommodityProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoDeleteSuccessTest() = runTest {
        val commodityToDelete =
            IctrlCommodity(id = IctrlCommodityId("123"), lock = IctrlCommodityLock("123-234-abc-ABC"))
        val ctx = IctrlContext(
            command = command,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.TEST,
            commodityRequest = commodityToDelete,
        )
        processor.exec(ctx)
        assertEquals(IctrlState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initCommodity.id, ctx.commodityResponse.id)
        assertEquals(initCommodity.name, ctx.commodityResponse.name)
        assertEquals(initCommodity.description, ctx.commodityResponse.description)
        assertEquals(initCommodity.manufacturer, ctx.commodityResponse.manufacturer)
        assertEquals(initCommodity.receiptQuantity, ctx.commodityResponse.receiptQuantity)
        assertEquals(initCommodity.commodityType, ctx.commodityResponse.commodityType)
        assertEquals(initCommodity.visibility, ctx.commodityResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}