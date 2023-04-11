package ru.otus.otuskotlin.incomingControl

import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext

private val processor = IctrlCommodityProcessor()
suspend fun process(ctx: IctrlContext) = processor.exec(ctx)