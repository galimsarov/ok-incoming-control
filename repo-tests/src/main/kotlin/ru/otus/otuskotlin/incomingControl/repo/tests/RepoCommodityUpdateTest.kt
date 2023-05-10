package ru.otus.otuskotlin.incomingControl.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityRequest
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommodityUpdateTest {
    abstract val repo: ICommodityRepository
    protected open val updateSucc = initObjects[0]
    protected val updateConc = initObjects[1]
    private val updateIdNotFound = IctrlCommodityId("ad-repo-update-not-found")
    protected val lockBad = IctrlCommodityLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = IctrlCommodityLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        IctrlCommodity(
            id = updateSucc.id,
            name = "update object",
            description = "update object description",
            manufacturer = "update object manufacturer",
            receiptQuantity = "update object receiptQuantity",
            commodityType = IctrlCommodityType.FASTENER_PART,
            ownerId = IctrlUserId("owner-123"),
            visibility = IctrlVisibility.VISIBLE_TO_GROUP,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = IctrlCommodity(
        id = updateIdNotFound,
        name = "update object not found",
        description = "update object not found description",
        manufacturer = "update object not found manufacturer",
        receiptQuantity = "update object not found receiptQuantity",
        commodityType = IctrlCommodityType.FASTENER_PART,
        ownerId = IctrlUserId("owner-123"),
        visibility = IctrlVisibility.VISIBLE_TO_GROUP,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc = IctrlCommodity(
        id = updateConc.id,
        name = "update object not found",
        description = "update object not found description",
        manufacturer = "update object not found manufacturer",
        receiptQuantity = "123",
        commodityType = IctrlCommodityType.FASTENER_PART,
        ownerId = IctrlUserId("owner-123"),
        visibility = IctrlVisibility.VISIBLE_TO_GROUP,
        lock = lockBad,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateCommodity(DbCommodityRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.manufacturer, result.data?.manufacturer)
        assertEquals(reqUpdateSucc.receiptQuantity, result.data?.receiptQuantity)
        assertEquals(reqUpdateSucc.commodityType, result.data?.commodityType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateCommodity(DbCommodityRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runTest {
        val result = repo.updateCommodity(DbCommodityRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitCommodities("update") {
        override val initObjects: List<IctrlCommodity> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}