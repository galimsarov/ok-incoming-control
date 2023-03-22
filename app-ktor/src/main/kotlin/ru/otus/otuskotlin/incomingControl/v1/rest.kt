package ru.otus.otuskotlin.incomingControl.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Commodity() {
    route("commodity") {
        post("create") {
            call.createCommodity()
        }
        post("read") {
            call.readCommodity()
        }
        post("update") {
            call.updateCommodity()
        }
        post("delete") {
            call.deleteCommodity()
        }
        post("search") {
            call.searchCommodity()
        }
    }
}