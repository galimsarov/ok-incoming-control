package ru.otus.otuskotlin.incomingControl.biz.auth

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.*
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlCommodityPermissionClient
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlPrincipalModel
import ru.otus.otuskotlin.incomingControl.common.permissions.IctrlUserGroups
import ru.otus.otuskotlin.incomingControl.repo.inmemory.CommodityRepoInMemory
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CommodityCrudAuthTest {
    @Test
    fun createSuccessTest() = runTest {
        val userId = IctrlUserId("123")
        val repo = CommodityRepoInMemory()
        val processor = IctrlCommodityProcessor(settings = IctrlCorSettings(repoTest = repo))
        val context = IctrlContext(
            workMode = IctrlWorkMode.TEST,
            commodityRequest = IctrlCommodityStub.prepareResult {
                permissionsClient.clear()
                id = IctrlCommodityId.NONE
            },
            command = IctrlCommand.CREATE,
            principal = IctrlPrincipalModel(id = userId, groups = setOf(IctrlUserGroups.USER, IctrlUserGroups.TEST))
        )

        processor.exec(context)
        assertEquals(IctrlState.FINISHING, context.state)
        with(context.commodityResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, IctrlCommodityPermissionClient.READ)
            assertContains(permissionsClient, IctrlCommodityPermissionClient.UPDATE)
            assertContains(permissionsClient, IctrlCommodityPermissionClient.DELETE)
//            assertFalse { permissionsClient.contains(PermissionModel.CONTACT) }
        }
    }

    @Test
    fun readSuccessTest() = runTest {
        val commodityObj = IctrlCommodityStub.get()
        val userId = commodityObj.ownerId
        val commodityId = commodityObj.id
        val repo = CommodityRepoInMemory(initObjects = listOf(commodityObj))
        val processor = IctrlCommodityProcessor(settings = IctrlCorSettings(repoTest = repo))
        val context = IctrlContext(
            command = IctrlCommand.READ,
            workMode = IctrlWorkMode.TEST,
            commodityRequest = IctrlCommodity(id = commodityId),
            principal = IctrlPrincipalModel(id = userId, groups = setOf(IctrlUserGroups.USER, IctrlUserGroups.TEST))
        )
        processor.exec(context)
        assertEquals(IctrlState.FINISHING, context.state)
        with(context.commodityResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, IctrlCommodityPermissionClient.READ)
            assertContains(permissionsClient, IctrlCommodityPermissionClient.UPDATE)
            assertContains(permissionsClient, IctrlCommodityPermissionClient.DELETE)
//            assertFalse { context.responseAd.permissions.contains(PermissionModel.CONTACT) }
        }
    }
}