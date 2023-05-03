package ru.otus.otuskotlin.incomingControl.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlUserId
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityFilterRequest
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommoditySearchTest {
    abstract val repo: ICommodityRepository

    protected open val initializedObjects: List<IctrlCommodity> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchCommodity(DbCommodityFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[2]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitCommodities("search") {

        val searchOwnerId = IctrlUserId("owner-124")
        override val initObjects: List<IctrlCommodity> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", ownerId = searchOwnerId),
        )
    }

}