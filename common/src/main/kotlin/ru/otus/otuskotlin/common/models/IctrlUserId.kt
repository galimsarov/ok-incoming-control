package ru.otus.otuskotlin.common.models

@JvmInline
value class IctrlUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = IctrlUserId("")
    }
}