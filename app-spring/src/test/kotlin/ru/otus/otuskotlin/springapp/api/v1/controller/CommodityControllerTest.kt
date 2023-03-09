package ru.otus.otuskotlin.springapp.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.common.IctrlContext
import ru.otus.otuskotlin.mappers.v1.*
import ru.otus.otuskotlin.stubs.IctrlCommodityStub

// Temporary simple test with stubs
@WebMvcTest(CommodityController::class)
class CommodityControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createCommodity() = testStubCommodity(
        "/v1/commodity/create",
        CommodityCreateRequest(),
        IctrlContext().apply { commodityResponse = IctrlCommodityStub.get() }.toTransportCreate()
    )

    @Test
    fun readAd() = testStubCommodity(
        "/v1/commodity/read",
        CommodityReadRequest(),
        IctrlContext().apply { commodityResponse = IctrlCommodityStub.get() }.toTransportRead()
    )

    @Test
    fun updateAd() = testStubCommodity(
        "/v1/commodity/update",
        CommodityUpdateRequest(),
        IctrlContext().apply { commodityResponse = IctrlCommodityStub.get() }.toTransportUpdate()
    )

    @Test
    fun deleteAd() = testStubCommodity(
        "/v1/commodity/delete",
        CommodityDeleteRequest(),
        IctrlContext().toTransportDelete()
    )

    @Test
    fun searchAd() = testStubCommodity(
        "/v1/commodity/search",
        CommoditySearchRequest(),
        IctrlContext().apply { commoditiesResponse.add(IctrlCommodityStub.get()) }.toTransportSearch()
    )

    private fun <Req : Any, Res : Any> testStubCommodity(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        val request = mapper.writeValueAsString(requestObj)
        val response = mapper.writeValueAsString(responseObj)

        mvc.perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }
}