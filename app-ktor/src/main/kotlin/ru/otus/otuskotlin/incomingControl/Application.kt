package ru.otus.otuskotlin.incomingControl

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain.main
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1Mapper
import ru.otus.otuskotlin.incomingControl.plugins.initAppSettings
import ru.otus.otuskotlin.incomingControl.plugins.initPlugins
import ru.otus.otuskotlin.incomingControl.v1.v1Commodity
import ru.otus.otuskotlin.incomingControl.v1.wsHandlerV1

fun main(args: Array<String>): Unit = main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(appSettings: IctrlAppSettings = initAppSettings()) {
    initPlugins(appSettings)

    install(CallLogging) { level = Level.INFO }
    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }
    install(DefaultHeaders)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v1") {
            v1Commodity(appSettings)
        }

        webSocket("/ws/v1") {
            wsHandlerV1(appSettings)
        }
    }
}