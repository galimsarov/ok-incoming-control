package ru.otus.otuskotlin.incomingControl.repo.sql

data class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/incomingcontrol",
    val user: String = "postgres",
    val password: String = "incomingcontrol-pass",
    val schema: String = "incomingcontrol",
    // Удалять таблицы при старте - нужно для тестирования
    val dropDatabase: Boolean = false,
)