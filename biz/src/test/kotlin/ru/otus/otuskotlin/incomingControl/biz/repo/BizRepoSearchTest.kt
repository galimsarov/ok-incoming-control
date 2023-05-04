package ru.otus.otuskotlin.incomingControl.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommoditiesResponse
import ru.otus.otuskotlin.incomingControl.repo.tests.CommodityRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {
    private val userId = IctrlUserId("321")
    private val command = IctrlCommand.SEARCH
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
            invokeSearchCommodity = {
                DbCommoditiesResponse(
                    isSuccess = true,
                    data = listOf(initCommodity),
                )
            }
        )
    }
    private val settings by lazy { IctrlCorSettings(repoTest = repo) }
    private val processor by lazy { IctrlCommodityProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = IctrlContext(
            command = command,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.TEST,
            commodityFilterRequest = IctrlCommodityFilter(searchString = "ab"),
        )
        processor.exec(ctx)
        assertEquals(IctrlState.FINISHING, ctx.state)
        assertEquals(1, ctx.commoditiesResponse.size)
    }
}