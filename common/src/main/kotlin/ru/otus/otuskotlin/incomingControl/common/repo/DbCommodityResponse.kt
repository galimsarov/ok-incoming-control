package ru.otus.otuskotlin.incomingControl.common.repo

import ru.otus.otuskotlin.incomingControl.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityLock
import ru.otus.otuskotlin.incomingControl.common.models.IctrlError
import ru.otus.otuskotlin.incomingControl.common.helpers.errorEmptyId as ictrlErrorEmptyId
import ru.otus.otuskotlin.incomingControl.common.helpers.errorNotFound as ictrlErrorNotFound

class DbCommodityResponse(
    override val data: IctrlCommodity?,
    override val isSuccess: Boolean,
    override val errors: List<IctrlError> = emptyList()
) : IDbResponse<IctrlCommodity> {
    companion object {
        val MOCK_SUCCESS_EMPTY = DbCommodityResponse(null, true)
        fun success(result: IctrlCommodity) = DbCommodityResponse(result, true)
        fun error(errors: List<IctrlError>) = DbCommodityResponse(null, false, errors)
        fun error(error: IctrlError, data: IctrlCommodity? = null) = DbCommodityResponse(data, false, listOf(error))

        val errorEmptyId = error(ictrlErrorEmptyId)

        fun errorConcurrent(lock: IctrlCommodityLock, commodity: IctrlCommodity?) = error(
            errorRepoConcurrency(lock, commodity?.lock?.let { IctrlCommodityLock(it.asString()) }),
            commodity
        )

        val errorNotFound = error(ictrlErrorNotFound)
    }
}