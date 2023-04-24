package ru.otus.otuskotlin.incomingControl.v1

import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import ru.otus.otuskotlin.incomingControl.api.v1.apiV1Mapper
import ru.otus.otuskotlin.incomingControl.api.v1.models.IRequest
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.helpers.addError
import ru.otus.otuskotlin.incomingControl.common.helpers.asIctrlError
import ru.otus.otuskotlin.incomingControl.common.helpers.isUpdatableCommand
import ru.otus.otuskotlin.incomingControl.mappers.v1.fromTransport
import ru.otus.otuskotlin.incomingControl.mappers.v1.toTransportCommodity
import ru.otus.otuskotlin.incomingControl.mappers.v1.toTransportInit
import ru.otus.otuskotlin.incomingControl.process
import java.util.*

private val sessions: MutableSet<WebSocketSession> = Collections.synchronizedSet(LinkedHashSet())

suspend fun WebSocketSession.wsHandlerV1() {
    sessions.add(this)

    // Handle init request
    val ctx = IctrlContext()
    val init = apiV1Mapper.writeValueAsString(ctx.toTransportInit())
    outgoing.send(Frame.Text(init))

    // Handle flow
    incoming.receiveAsFlow().mapNotNull {
        val frame = it as? Frame.Text ?: return@mapNotNull

        val jsonStr = frame.readText()
        val context = IctrlContext()

        // Handle without flow destruction
        try {
            val request = apiV1Mapper.readValue<IRequest>(jsonStr)
            context.fromTransport(request)
            process(ctx)

            val result = apiV1Mapper.writeValueAsString(context.toTransportCommodity())

            // If change request, response is sent to everyone
            if (context.isUpdatableCommand()) {
                sessions.forEach { session -> session.send(Frame.Text(result)) }
            } else {
                outgoing.send(Frame.Text(result))
            }
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        } catch (t: Throwable) {
            context.addError(t.asIctrlError())

            val result = apiV1Mapper.writeValueAsString(context.toTransportInit())
            outgoing.send(Frame.Text(result))
        }
    }.collect()
}