package ru.otus.otuskotlin.incomingcontrol.m1l5.homework.hard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    val dictionaryApi = DictionaryApi()
    val words = FileReader.readFile().split(" ", "\n").toSet()

    val dictionaries = findWords(dictionaryApi, words, Locale.EN)

    dictionaries.map { dictionary ->
        print("For word ${dictionary.word} i found examples: ")
        println(dictionary.meanings.map { definition -> definition.definitions.map { it.example } })
    }
}

private fun findWords(dictionaryApi: DictionaryApi, words: Set<String>, locale: Locale) = runBlocking {
    val requests = words.map { async(Dispatchers.IO) { dictionaryApi.findWord(locale, it) } }
    requests.map { (it.await()) }
}

object FileReader {
    fun readFile(): String =
        File(
            this::class.java.classLoader.getResource("words.txt")?.toURI() ?: throw RuntimeException("Can't read file")
        ).readText()
}