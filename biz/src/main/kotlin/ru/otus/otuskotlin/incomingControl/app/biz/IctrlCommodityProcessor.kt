package ru.otus.otuskotlin.incomingControl.app.biz

import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub

class IctrlCommodityProcessor {
    fun exec(ctx: IctrlContext) {
        ctx.commodityResponse = IctrlCommodityStub.get()
    }
}