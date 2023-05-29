package ru.otus.otuskotlin.incomingControl

import ru.otus.otuskotlin.incomingControl.base.KtorAuthConfig
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings

data class IctrlAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: IctrlCorSettings,
    val processor: IctrlCommodityProcessor = IctrlCommodityProcessor(settings = corSettings),
    val auth: KtorAuthConfig = KtorAuthConfig.NONE,
)