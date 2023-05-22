package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError

data class DbCommoditiesResponse(
    override val data: List<IctrlCommodity>?,
    override val isSuccess: Boolean,
    override val errors: List<IctrlError> = emptyList(),
) : IDbResponse<List<IctrlCommodity>> {
    companion object {
        val MOCK_SUCCESS_EMPTY = DbCommoditiesResponse(emptyList(), true)
        fun success(result: List<IctrlCommodity>) = DbCommoditiesResponse(result, true)
        fun error(errors: List<IctrlError>) = DbCommoditiesResponse(null, false, errors)
        fun error(error: IctrlError) = DbCommoditiesResponse(null, false, listOf(error))
    }
}
