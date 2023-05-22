package ru.otus.otuskotlin.incomingControl.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityRequest
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommodityCreateTest {
    abstract val repo: ICommodityRepository

    protected open val lockNew: IctrlCommodityLock = IctrlCommodityLock("20000000-0000-0000-0000-000000000002")

    private val createObj = IctrlCommodity(
        name = "create object",
        description = "create object description",
        manufacturer = "create object manufacturer",
        receiptQuantity = "123",
        commodityType = IctrlCommodityType.FASTENER_PART,
        ownerId = IctrlUserId("owner-123"),
        visibility = IctrlVisibility.VISIBLE_TO_GROUP
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createCommodity(DbCommodityRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: IctrlCommodityId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.name, result.data?.name)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.manufacturer, result.data?.manufacturer)
        assertEquals(expected.receiptQuantity, result.data?.receiptQuantity)
        assertEquals(expected.commodityType, result.data?.commodityType)
        assertEquals(expected.id, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitCommodities("create") {
        override val initObjects: List<IctrlCommodity> = emptyList()
    }
}