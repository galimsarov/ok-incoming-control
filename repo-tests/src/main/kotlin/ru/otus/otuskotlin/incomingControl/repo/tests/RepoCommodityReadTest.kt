package ru.otus.otuskotlin.incomingControl.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityIdRequest
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommodityReadTest {
    abstract val repo: ICommodityRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readCommodity(DbCommodityIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readCommodity(DbCommodityIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitCommodities("delete") {
        override val initObjects: List<IctrlCommodity> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = IctrlCommodityId("ad-repo-read-notFound")
    }
}