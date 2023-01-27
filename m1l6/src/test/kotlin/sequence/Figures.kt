package ru.otus.otuskotlin.incomingcontrol.m1l6.sequence

import org.junit.Test

enum class Color { YELLOW, GREEN, BLUE, RED, VIOLET }

enum class Shape { SQUARE, CIRCLE, TRIANGLE, RHOMBUS }

data class Figure(val color: Color, val shape: Shape)

class FiguresTest {
    companion object {
        private val testFigure = listOf(
            Figure(Color.GREEN, Shape.CIRCLE),
            Figure(Color.VIOLET, Shape.SQUARE),
            Figure(Color.BLUE, Shape.RHOMBUS),
            Figure(Color.RED, Shape.TRIANGLE)
        )
    }

    @Test
    fun collection() {
        var counter = 0
        val figure = testFigure
            .map {
                counter++
                println("Change color")
                it.copy(color = Color.YELLOW)
            }
            .first {
                counter++
                println("Filter by shape")
                it.shape == Shape.SQUARE
            }
        println("Figure: $figure")
        println("Counter: $counter")
    }

    @Test
    fun sequence() {
        var counter = 0
        val figure = testFigure.asSequence()
            .map {
                counter++
                println("Change color")
                it.copy(color = Color.YELLOW)
            }
            .first {
                counter++
                println("Filter by shape")
                it.shape == Shape.SQUARE
            }
        println("Figure: $figure")
        println("Counter: $counter")
    }
}