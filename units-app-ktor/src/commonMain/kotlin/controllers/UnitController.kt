package com.crowdproj.marketplace.units.app.controllers

import com.crowdproj.marketplace.units.api.v1.apiMapper
import com.crowdproj.marketplace.units.api.v1.models.*
import com.crowdproj.marketplace.units.common.UntsContext
import com.crowdproj.marketplace.units.mappers.*
import com.crowdproj.marketplace.units.stubs.UntsUnitStub
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

suspend fun ApplicationCall.createUnit() {
    val request = apiMapper.decodeFromString<UnitCreateRequest>(receiveText())
    val context = UntsContext()
    context.fromTransport(request)
    context.unitResponse = UntsUnitStub.getUnit()
    respond(apiMapper.encodeToString(context.toTransportCreate()))
}

suspend fun ApplicationCall.readUnit() {
    val request = apiMapper.decodeFromString<UnitReadRequest>(receiveText())
    val context = UntsContext()
    context.fromTransport(request)
    context.unitResponse = UntsUnitStub.getUnit()
    respond(apiMapper.encodeToString(context.toTransportRead()))
}

suspend fun ApplicationCall.updateUnit() {
    val request = apiMapper.decodeFromString<UnitUpdateRequest>(receiveText())
    val context = UntsContext()
    context.fromTransport(request)
    context.unitResponse = UntsUnitStub.getUnit()
    respond(apiMapper.encodeToString(context.toTransportUpdate()))
}

suspend fun ApplicationCall.deleteUnit(){
    val request = apiMapper.decodeFromString<UnitDeleteRequest>(receiveText())
    val context = UntsContext()
    context.fromTransport(request)
    context.unitResponse = UntsUnitStub.getDeletedUnit()
    respond(apiMapper.encodeToString(context.toTransportDelete()))
}

suspend fun ApplicationCall.searchUnits() {
    val request = apiMapper.decodeFromString<UnitSearchRequest>(receiveText())
    val context = UntsContext()
    context.fromTransport(request)
    context.unitsResponse.addAll(UntsUnitStub.getUnitList())
    respond(apiMapper.encodeToString(context.toTransportSearch()))
}
