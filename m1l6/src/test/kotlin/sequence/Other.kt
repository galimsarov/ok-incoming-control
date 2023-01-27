package ru.otus.otuskotlin.incomingcontrol.m1l6.sequence

import org.junit.Test

class OtherTest {
    @Test
    fun collectionIsNotLazy() {
        var counter = 0
        val list = listOf(1, 2, 3, 4)
            .map {
                counter++
                it * it
            }
            .take(2)
        println("List: $list")
        println("Counter: $counter")
    }

    @Test
    fun sequenceIsLazy() {
        var counter = 0
        val sequence = sequenceOf(1, 2, 3, 4)
            .map {
                counter++
                it * it
            }
            .take(2)

        println("Sequence: $sequence")
        println("Counter: $counter")

        val result = sequence
            .toList()

        println("Result: $result")
        println("Counter: $counter")
    }

    @Test
    fun blockingCall() {
        val sequence = sequenceOf(1, 2, 3)
            .map {
                println("Make blocking call to API")
                Thread.sleep(3000)
                it + 1
            }
            .toList()
        println("Sequence: $sequence")
    }
}