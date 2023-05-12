package com.crowdproj.marketplace.units.app.controllers

import com.crowdproj.marketplace.units.api.v1.apiMapper
import com.crowdproj.marketplace.units.api.v1.encodeResponse
import com.crowdproj.marketplace.units.api.v1.models.*
import com.crowdproj.marketplace.units.common.UntsContext
import com.crowdproj.marketplace.units.common.helpers.addError
import com.crowdproj.marketplace.units.common.helpers.asUnitError
import com.crowdproj.marketplace.units.common.helpers.isUpdatableCommand
import com.crowdproj.marketplace.units.mappers.*
import com.crowdproj.marketplace.units.stubs.UntsUnitStub
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.decodeFromString

val sessions = mutableSetOf<WebSocketSession>()

suspend fun WebSocketSession.wsHandlerV1() {
    sessions.add(this)

    // Handle init request
    val ctx = UntsContext()
    val init = apiMapper.encodeResponse(ctx.toTransportInit())
    outgoing.send(Frame.Text(init))

    // Handle flow
    incoming.receiveAsFlow().mapNotNull { it ->
        val frame = it as? Frame.Text ?: return@mapNotNull

        val jsonStr = frame.readText()
        val context = UntsContext()

        // Handle without flow destruction
        try {
            val request = apiMapper.decodeFromString<IUnitRequest>(jsonStr)
            context.fromTransport(request)
            context.fillStubResponse(request)

            val result = apiMapper.encodeResponse(context.toTransport())

            // If change request, response is sent to everyone
            if (context.isUpdatableCommand()) {
                sessions.forEach {
                    it.send(Frame.Text(result))
                }
            } else {
                outgoing.send(Frame.Text(result))
            }
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        } catch (t: Throwable) {
            context.addError(t.asUnitError())

            val result = apiMapper.encodeResponse(context.toTransportInit())
            outgoing.send(Frame.Text(result))
        }
    }.collect()

    sessions.remove(this)
}

private fun UntsContext.fillStubResponse(request: IUnitRequest) {
    when (request) {
        is UnitSearchRequest -> this.unitsResponse.addAll(UntsUnitStub.getUnitList())
        is UnitDeleteRequest -> this.unitResponse = UntsUnitStub.getDeletedUnit()
        else -> this.unitResponse = UntsUnitStub.getUnit()
    }
}
