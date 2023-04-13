package ru.otus.otuskotlin.incomingControl.biz

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {
    private val command = IctrlCommand.CREATE
    private val processor by lazy { IctrlCommodityProcessor() }

    @Test
    fun correctTitle() = validationNameCorrect(command, processor)

    @Test
    fun trimTitle() = validationNameTrim(command, processor)

    @Test
    fun emptyTitle() = validationNameEmpty(command, processor)

    @Test
    fun badSymbolsTitle() = validationNameSymbols(command, processor)

    @Test
    fun correctDescription() = validationDescriptionCorrect(command, processor)

    @Test
    fun trimDescription() = validationDescriptionTrim(command, processor)

    @Test
    fun emptyDescription() = validationDescriptionEmpty(command, processor)

    @Test
    fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)
}