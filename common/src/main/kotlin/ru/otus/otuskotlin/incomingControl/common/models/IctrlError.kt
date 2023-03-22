package ru.otus.otuskotlin.incomingControl.common.models

data class IctrlError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
