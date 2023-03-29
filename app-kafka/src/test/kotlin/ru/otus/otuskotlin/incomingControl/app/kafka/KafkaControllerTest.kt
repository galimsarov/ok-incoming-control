package ru.otus.otuskotlin.incomingControl.app.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1RequestSerialize
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1ResponseDeserialize
import ru.otus.otuskotlin.incomingControl.api.v1.models.*
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        CommodityCreateRequest(
                            requestId = "11111111-1111-1111-1111-111111111111",
                            commodity = CommodityCreateObject(
                                name = "Some Commodity",
                                description = "some testing ad to check them all",
                                manufacturer = "some manufacturer",
                                receiptQuantity = "some quantity",
                                commodityType = CommodityType.FASTENER_PART,
                                visibility = CommodityVisibility.OWNER_ONLY,
                            ),
                            debug = CommodityDebug(
                                mode = CommodityRequestDebugMode.STUB,
                                stub = CommodityRequestDebugStubs.SUCCESS
                            )
                        )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<CommodityCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
        assertEquals("Болт", result.commodity?.name)
    }

    companion object {
        const val PARTITION = 0
    }
}