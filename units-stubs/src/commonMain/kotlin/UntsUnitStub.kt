package com.crowdproj.marketplace.units.stubs

import com.crowdproj.marketplace.units.common.models.*

object UntsUnitStub {
    val UNIT_KILOGRAM: UntsUnit
        get() = UntsUnit(
            id = UntsUnitId("1"),
            unit = "Kg",
            alias = "Kilogram",
            status = UntsUnitStatus.CONFIRMED,
            conversion = UntsConversionParameters(),
            description = "Unit of mass"
        )

    val UNIT_GRAM: UntsUnit
        get() = UntsUnit(
            id = UntsUnitId("2"),
            unit = "g",
            alias = "gram",
            status = UntsUnitStatus.CONFIRMED,
            conversion = UntsConversionParameters(A = 1000.0),
            systemUnitId = UntsUnitId("1"),
        )

    fun getUnit(): UntsUnit = UNIT_KILOGRAM.copy()

    fun getDeletedUnit(): UntsUnit = UNIT_KILOGRAM.copy(status = UntsUnitStatus.DELETED)

    fun getUnitList() = listOf(
        UNIT_KILOGRAM.copy(),
        UNIT_GRAM.copy(),
    )
}
