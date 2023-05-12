package com.crowdproj.marketplace.units.common.helpers

import com.crowdproj.marketplace.units.common.UntsContext
import com.crowdproj.marketplace.units.common.models.UntsError

fun Throwable.asUnitError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = UntsError(
    code = code,
    group = group,
    field = "",
    title = message,
    description = this.toString(),
    exception = this,
)

fun UntsContext.addError(vararg error: UntsError) = errors.addAll(error)
