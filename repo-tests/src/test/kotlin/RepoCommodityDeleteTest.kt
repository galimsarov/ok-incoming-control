import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodity
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.common.repo.DbCommodityIdRequest
import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoCommodityDeleteTest {
    abstract val repo: ICommodityRepository
    protected open val deleteSucc = initObjects[0]

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteCommodity(DbCommodityIdRequest(deleteSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readCommodity(DbCommodityIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitCommodities("delete") {
        override val initObjects: List<IctrlCommodity> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val notFoundId = IctrlCommodityId("ad-repo-delete-notFound")
    }
}