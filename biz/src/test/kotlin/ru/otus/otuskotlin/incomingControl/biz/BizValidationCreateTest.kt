package ru.otus.otuskotlin.incomingControl.biz

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import kotlin.test.Test

class BizValidationCreateTest {
    private val command = IctrlCommand.CREATE
    private val processor by lazy { IctrlCommodityProcessor() }

    @Test
    fun correctName() = validationNameCorrect(command, processor)

    @Test
    fun trimName() = validationNameTrim(command, processor)

    @Test
    fun emptyName() = validationNameEmpty(command, processor)

    @Test
    fun badSymbolsName() = validationNameSymbols(command, processor)

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
}