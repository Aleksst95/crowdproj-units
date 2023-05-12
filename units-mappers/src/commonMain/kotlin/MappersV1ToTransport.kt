package com.crowdproj.marketplace.units.mappers

import com.crowdproj.marketplace.units.api.v1.models.*
import com.crowdproj.marketplace.units.common.UntsContext
import com.crowdproj.marketplace.units.common.models.*
import com.crowdproj.marketplace.units.mappers.exceptions.UnknownUntsCommand
import com.crowdproj.marketplace.units.mappers.exceptions.UnknownUntsUnitStatus

fun UntsContext.toTransport(): IUnitResponse = when (val cmd = command) {
    UntsCommand.CREATE -> toTransportCreate()
    UntsCommand.READ -> toTransportRead()
    UntsCommand.UPDATE -> toTransportUpdate()
    UntsCommand.DELETE -> toTransportDelete()
    UntsCommand.SEARCH -> toTransportSearch()
    UntsCommand.INIT -> toTransportInit()
    UntsCommand.NONE -> throw UnknownUntsCommand(cmd)
}

fun UntsContext.toTransportCreate() = UnitCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == UntsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransport()
)

fun UntsContext.toTransportRead() = UnitReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == UntsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransport()
)

fun UntsContext.toTransportUpdate() = UnitUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == UntsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransport()
)

fun UntsContext.toTransportDelete() = UnitDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == UntsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    unit = unitResponse.toTransport()
)

fun UntsContext.toTransportSearch() = UnitSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == UntsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    units = unitsResponse.toTransport()
)

fun UntsContext.toTransportInit() = UnitInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == UntsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors()
)

fun List<UntsUnit>.toTransport(): List<UnitResponseObject> = this
    .map { it.toTransport() }
    .toList()

private fun UntsUnit.toTransport(): UnitResponseObject = UnitResponseObject(
    id = id.asString(),
    unit = unit,
    alias = alias,
    description = description.takeIf { it.isNotBlank() },
    conversion = conversion.toTransport(),
    status = status.toTransport(),
    systemUnitId = systemUnitId.takeIf { it != UntsUnitId.NONE }?.asString(),
)

private fun UntsUnitStatus.toTransport() = when (this) {
    UntsUnitStatus.SUGGESTED -> UnitStatus.SUGGESTED
    UntsUnitStatus.CONFIRMED -> UnitStatus.CONFIRMED
    UntsUnitStatus.DEPRECATED -> UnitStatus.DEPRECATED
    UntsUnitStatus.DELETED -> UnitStatus.DELETED
    UntsUnitStatus.NONE -> throw UnknownUntsUnitStatus(this)
}

private fun List<UntsError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun UntsError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
)

private fun UntsConversionParameters.toTransport() = UnitConversionParameters(
    A, B
)
