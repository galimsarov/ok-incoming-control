package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.models.IctrlError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<IctrlError>
}