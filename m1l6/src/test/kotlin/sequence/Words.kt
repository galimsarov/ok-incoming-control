package ru.otus.otuskotlin.incomingcontrol.m1l6.sequence

import org.junit.Test

fun processAsList(words: List<String>, wordLength: Int, take: Int, printOperation: Boolean) {
    println("Processing list")
    var counter = 0

    val lengthsList = words
        .filter {
            counter++
            if (printOperation) println("filter: $it")

            it.length > wordLength
        }
        .map {
            counter++
            if (printOperation) println("length: ${it.length}")

            it.length
        }
        .take(take)

    println("Lengths of first $take words longer than $wordLength chars:")
    println(lengthsList)

    println("List counter: $counter")
    println()
}

fun processAsSeq(words: List<String>, wordLength: Int, take: Int, printOperation: Boolean) {
    println("Processing sequence")
    var counter = 0

    //convert the List to a Sequence
    val wordsSequence = words.asSequence()

    val lengthsSequence = wordsSequence
        .filter {
            counter++
            if (printOperation) println("filter: $it")

            it.length > wordLength
        }
        .map {
            counter++
            if (printOperation) println("length: ${it.length}")

            it.length
        }
        .take(take)
        .toList()

    println("Lengths of first $take words longer than $wordLength chars")
    println(lengthsSequence)

    println("Sequence counter: $counter")
    println()
}

class WordsTest {
    @Test
    fun smallSequence() {
        val words = "The quick brown fox jumps over the lazy dog".split(" ")

        val wordLength = 3
        val take = 3
        val printOperation = false

        processAsList(words, wordLength, take, printOperation)
        processAsSeq(words, wordLength, take, printOperation)
    }

    @Test
    fun bigSequence() {
        val words = ("So, the sequences let you avoid building results of intermediate steps, " +
            "therefore improving the performance of the whole collection processing chain. " +
            "However, the lazy nature of sequences adds some overhead which may be significant " +
            "when processing smaller collections or doing simpler computations. Hence, you should consider both " +
            "Sequence and Iterable and decide which one is better for your case.")
            .split(" ")

        val wordLength = 3
        val take = 10

        val printOperation = false

        processAsList(words, wordLength, take, printOperation)
        processAsSeq(words, wordLength, take, printOperation)
    }
}