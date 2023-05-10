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

class BizRepoUpdateTest {
    private val userId = IctrlUserId("321")
    private val command = IctrlCommand.UPDATE
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
            invokeUpdateCommodity = {
                DbCommodityResponse(
                    isSuccess = true,
                    data = IctrlCommodity(
                        id = IctrlCommodityId("123"),
                        name = "xyz",
                        description = "xyz",
                        manufacturer = "xyz",
                        receiptQuantity = "123",
                        commodityType = IctrlCommodityType.FASTENER_PART,
                        visibility = IctrlVisibility.VISIBLE_TO_GROUP,
                    )
                )
            }
        )
    }
    private val settings by lazy { IctrlCorSettings(repoTest = repo) }
    private val processor by lazy { IctrlCommodityProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoUpdateSuccessTest() = runTest {
        val commodityToUpdate = IctrlCommodity(
            id = IctrlCommodityId("123"),
            name = "xyz",
            description = "xyz",
            manufacturer = "xyz",
            receiptQuantity = "123",
            commodityType = IctrlCommodityType.FASTENER_PART,
            visibility = IctrlVisibility.VISIBLE_TO_GROUP,
            lock = IctrlCommodityLock("123-234-abc-ABC")
        )
        val ctx = IctrlContext(
            command = command,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.TEST,
            commodityRequest = commodityToUpdate,
        )
        processor.exec(ctx)
        assertEquals(IctrlState.FINISHING, ctx.state)
        assertEquals(commodityToUpdate.id, ctx.commodityResponse.id)
        assertEquals(commodityToUpdate.name, ctx.commodityResponse.name)
        assertEquals(commodityToUpdate.description, ctx.commodityResponse.description)
        assertEquals(commodityToUpdate.manufacturer, ctx.commodityResponse.manufacturer)
        assertEquals(commodityToUpdate.receiptQuantity, ctx.commodityResponse.receiptQuantity)
        assertEquals(commodityToUpdate.commodityType, ctx.commodityResponse.commodityType)
        assertEquals(commodityToUpdate.visibility, ctx.commodityResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}