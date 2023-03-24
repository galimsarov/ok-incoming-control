package ru.otus.otuskotlin.incomingControl

import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.config.RabbitConfig
import ru.otus.otuskotlin.incomingControl.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.incomingControl.controller.RabbitController
import ru.otus.otuskotlin.incomingControl.processor.RabbitDirectProcessorV1

fun main() {
    val config = RabbitConfig()
    val commodityProcessor = IctrlCommodityProcessor()

    val producerConfigV1 = RabbitExchangeConfiguration(
        keyIn = "in-v1",
        keyOut = "out-v1",
        exchange = "transport-exchange",
        queue = "v1-queue",
        consumerTag = "v1-consumer",
        exchangeType = "direct"
    )

    val processor by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = producerConfigV1,
            processor = commodityProcessor
        )
    }

    val controller by lazy {
        RabbitController(
            processors = setOf(processor)
        )
    }
    controller.start()
}