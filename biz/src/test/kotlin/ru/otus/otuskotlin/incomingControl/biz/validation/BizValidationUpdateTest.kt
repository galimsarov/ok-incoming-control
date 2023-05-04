package ru.otus.otuskotlin.incomingControl.biz.validation

import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoStub
import kotlin.test.Test

class BizValidationUpdateTest {
    private val command = IctrlCommand.UPDATE
    private val settings by lazy { IctrlCorSettings(repoTest = CommodityRepoStub()) }
    private val processor by lazy { IctrlCommodityProcessor(settings) }

    @Test
    fun correctName() = validationNameCorrect(command, processor)

    @Test
    fun trimTitle() = validationNameTrim(command, processor)

    @Test
    fun emptyName() = validationNameEmpty(command, processor)

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

    @Test
    fun correctManufacturer() = validationManufacturerCorrect(command, processor)

    @Test
    fun trimManufacturer() = validationManufacturerTrim(command, processor)

    @Test
    fun emptyManufacturer() = validationManufacturerEmpty(command, processor)

    @Test
    fun badSymbolsManufacturer() = validationManufacturerSymbols(command, processor)

    @Test
    fun correctQuantity() = validationQuantityCorrect(command, processor)

    @Test
    fun trimQuantity() = validationQuantityTrim(command, processor)

    @Test
    fun emptyQuantity() = validationQuantityEmpty(command, processor)

    @Test
    fun badSymbolsQuantity() = validationQuantitySymbols(command, processor)

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun trimId() = validationIdTrim(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun badFormatId() = validationIdFormat(command, processor)

}