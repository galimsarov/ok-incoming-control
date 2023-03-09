package ru.otus.otuskotlin.common.models

data class IctrlError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
