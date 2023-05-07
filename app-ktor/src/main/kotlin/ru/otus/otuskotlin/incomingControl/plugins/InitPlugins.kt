package ru.otus.otuskotlin.incomingControl.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.incomingControl.IctrlAppSettings

fun Application.initPlugins(appSettings: IctrlAppSettings) {
    install(Routing)
    install(WebSockets)

    install(CORS) {
        allowNonSimpleContentTypes = true
        allowSameOrigin = true
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader("*")
        appSettings.appUrls.forEach {
            val split = it.split("://")
            println("$split")
            when (split.size) {
                2 -> allowHost(split[1].split("/")[0], listOf(split[0]))
                1 -> allowHost(
                    split[0].split("/")[0], listOf("http", "https")
                )
            }
        }
    }
    install(CachingHeaders)
    install(AutoHeadResponse)
}