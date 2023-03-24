package ru.otus.otuskotlin.incomingControl.processor

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.incomingControl.RabbitProcessorBase
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1Mapper
import ru.otus.otuskotlin.incomingControl.api.v1.models.IRequest
import ru.otus.otuskotlin.incomingControl.biz.IctrlCommodityProcessor
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.addError
import ru.otus.otuskotlin.incomingControl.common.helpers.asIctrlError
import ru.otus.otuskotlin.incomingControl.common.models.IctrlState
import ru.otus.otuskotlin.incomingControl.config.RabbitConfig
import ru.otus.otuskotlin.incomingControl.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.incomingControl.mappers.v1.fromTransport
import ru.otus.otuskotlin.incomingControl.mappers.v1.toTransportCommodity

class RabbitDirectProcessorV1(
    config: RabbitConfig,
    processorConfig: RabbitExchangeConfiguration,
    private val processor: IctrlCommodityProcessor = IctrlCommodityProcessor()
) : RabbitProcessorBase(config, processorConfig) {
    private val context = IctrlContext()

    override suspend fun Channel.processMessage(message: Delivery) {
        context.apply {
            timeStart = Clock.System.now()
        }

        apiV1Mapper.readValue(message.body, IRequest::class.java).run {
            context.fromTransport(this).also {
                println("TYPE: ${this::class.simpleName}")
            }
        }
        val response = processor.exec(context).run { context.toTransportCommodity() }
        apiV1Mapper.writeValueAsBytes(response).also {
            println("Publishing $response to ${processorConfig.exchange} exchange for keyOut ${processorConfig.keyOut}")
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }.also {
            println("published")
        }
    }

    override fun Channel.onError(e: Throwable) {
        e.printStackTrace()
        context.state = IctrlState.FAILING
        context.addError(error = arrayOf(e.asIctrlError()))
        val response = context.toTransportCommodity()
        apiV1Mapper.writeValueAsBytes(response).also {
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }
    }
}