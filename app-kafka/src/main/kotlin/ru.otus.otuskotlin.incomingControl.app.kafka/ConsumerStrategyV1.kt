package ru.otus.otuskotlin.incomingControl.app.kafka

import ru.otus.otuskotlin.incomingControl.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.incomingControl.api.v1.models.IRequest
import ru.otus.otuskotlin.incomingControl.api.v1.models.IResponse
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.mappers.v1.fromTransport
import ru.otus.otuskotlin.incomingControl.mappers.v1.toTransportCommodity

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: IctrlContext): String {
        val response: IResponse = source.toTransportCommodity()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: IctrlContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}