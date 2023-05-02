package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError

class DbCommodityResponse(
    override val data: IctrlCommodity?,
    override val isSuccess: Boolean,
    override val errors: List<IctrlError> = emptyList()
) : IDbResponse<IctrlCommodity> {
    companion object {
        val MOCK_SUCCESS_EMPTY = DbCommodityResponse(null, true)
        fun success(result: IctrlCommodity) = DbCommodityResponse(result, true)
        fun error(errors: List<IctrlError>) = DbCommodityResponse(null, false, errors)
        fun error(error: IctrlError) = DbCommodityResponse(null, false, listOf(error))
    }
}