package ru.otus.otuskotlin.incomingcontrol.m1l5.homework.easy

import kotlinx.coroutines.delay

suspend fun findNumberInList(toFind: Int, numbers: List<Int>): Int {
    delay(2_000)
    return numbers.firstOrNull { it == toFind } ?: -1
}