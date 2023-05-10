package ru.otus.otuskotlin.incomingControl.common.models

@JvmInline
value class IctrlCommodityLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = IctrlCommodityLock("")
    }
}