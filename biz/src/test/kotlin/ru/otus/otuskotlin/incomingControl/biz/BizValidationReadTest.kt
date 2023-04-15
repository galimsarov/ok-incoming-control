package ru.otus.otuskotlin.incomingControl.biz

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import kotlin.test.Test

class BizValidationReadTest {

    private val command = IctrlCommand.READ
    private val processor by lazy { IctrlCommodityProcessor() }

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun trimId() = validationIdTrim(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun badFormatId() = validationIdFormat(command, processor)

}