package ru.otus.otuskotlin.incomingControl.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.incomingControl.IctrlAppSettings

fun Route.v1Commodity(appSettings: IctrlAppSettings) {
    route("commodity") {
        post("create") {
            call.createCommodity(appSettings)
        }
        post("read") {
            call.readCommodity(appSettings)
        }
        post("update") {
            call.updateCommodity(appSettings)
        }
        post("delete") {
            call.deleteCommodity(appSettings)
        }
        post("search") {
            call.searchCommodity(appSettings)
        }
    }
}