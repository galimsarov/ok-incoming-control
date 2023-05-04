package ru.otus.otuskotlin.incomingControl.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityFilter
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.common.models.IctrlWorkMode
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {
    private val command = IctrlCommand.SEARCH
    private val settings by lazy { IctrlCorSettings(repoTest = CommodityRepoStub()) }
    private val processor by lazy { IctrlCommodityProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = IctrlContext(
            command = command,
            state = IctrlState.NONE,
            workMode = IctrlWorkMode.TEST,
            commodityFilterRequest = IctrlCommodityFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(IctrlState.FAILING, ctx.state)
    }
}