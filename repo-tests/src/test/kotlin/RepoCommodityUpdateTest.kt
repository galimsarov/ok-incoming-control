import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityRequest
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommodityUpdateTest {
    abstract val repo: ICommodityRepository
    protected open val updateSucc = initObjects[0]
    private val updateIdNotFound = IctrlCommodityId("ad-repo-update-not-found")

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

    companion object : BaseInitCommodities("update") {
        override val initObjects: List<IctrlCommodity> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}