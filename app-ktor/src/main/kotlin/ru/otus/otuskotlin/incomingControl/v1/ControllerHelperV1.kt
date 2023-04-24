package ru.otus.otuskotlin.incomingControl.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.incomingControl.api.v1.models.IRequest
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.mappers.v1.fromTransport
import ru.otus.otuskotlin.incomingControl.mappers.v1.toTransportCommodity
import ru.otus.otuskotlin.incomingControl.process

suspend inline fun <reified Q : IRequest> ApplicationCall.processV1() {
    val request = receive<Q>()
    val context = IctrlContext()
    context.fromTransport(request)
    process(context)
    respond(context.toTransportCommodity())
}