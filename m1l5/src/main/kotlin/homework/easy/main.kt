package ru.otus.otuskotlin.incomingcontrol.m1l5.homework.easy

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val numbers = generateNumbers()
    val toFind = 10
    val toFindOther = 1000

    val first = async { findNumberInList(toFind, numbers) }
    val second = async { findNumberInList(toFindOther, numbers) }

    val foundNumbers = listOf(first, second)

    foundNumbers.forEach {
        val number = it.await()
        if (number != -1) {
            println("Your number $number found!")
        } else {
            println("Not found number $toFind || $toFindOther")
        }
    }
}