package ru.otus.otuskotlin.incomingControl

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.testcontainers.containers.RabbitMQContainer
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1Mapper
import ru.otus.otuskotlin.incomingControl.api.v1.models.*
import ru.otus.otuskotlin.incomingControl.config.RabbitConfig
import ru.otus.otuskotlin.incomingControl.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.incomingControl.controller.RabbitController
import ru.otus.otuskotlin.incomingControl.processor.RabbitDirectProcessorV1
import ru.otus.otuskotlin.incomingControl.stubs.IctrlCommodityStub
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RabbitMqTest {
    companion object {
        const val exchange = "test-exchange"
        const val exchangeType = "direct"
    }

    val container by lazy {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
        RabbitMQContainer("rabbitmq:latest").apply {
            withExposedPorts(5672, 15672)
            withUser("guest", "guest")
            start()
        }
    }

    val rabbitMqTestPort: Int by lazy {
        container.getMappedPort(5672)
    }

    val config by lazy {
        RabbitConfig(
            port = rabbitMqTestPort
        )
    }

    val processorV1 by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = RabbitExchangeConfiguration(
                keyIn = "in-v1",
                keyOut = "out-v1",
                exchange = exchange,
                queue = "v1-queue",
                consumerTag = "test-tag",
                exchangeType = exchangeType
            )
        )
    }

    val controller by lazy {
        RabbitController(
            processors = setOf(processorV1)
        )
    }

    @BeforeTest
    fun tearUp() {
        controller.start()
    }

    @Test
    fun commodityCreateTestV1() {
        val keyOut = processorV1.processorConfig.keyOut
        val keyIn = processorV1.processorConfig.keyIn
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, apiV1Mapper.writeValueAsBytes(boltCreateV1))

                runBlocking {
                    withTimeoutOrNull(265L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = apiV1Mapper.readValue(responseJson, CommodityCreateResponse::class.java)
                val expected = IctrlCommodityStub.get()

                assertEquals(expected.name, response.commodity?.name)
                assertEquals(expected.description, response.commodity?.description)
            }
        }
    }

    private val boltCreateV1 = with(IctrlCommodityStub.get()) {
        CommodityCreateRequest(
            commodity = CommodityCreateObject(
                name = name,
                description = description
            ),
            requestType = "create",
            debug = CommodityDebug(
                mode = CommodityRequestDebugMode.STUB,
                stub = CommodityRequestDebugStubs.SUCCESS
            )
        )
    }
}