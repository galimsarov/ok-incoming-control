package ru.otus.otuskotlin.common.models

@JvmInline
value class IctrlRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = IctrlRequestId("")
    }
}