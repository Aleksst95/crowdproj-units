package com.crowdproj.marketplace.units.common.helpers

import com.crowdproj.marketplace.units.common.UntsContext
import com.crowdproj.marketplace.units.common.models.UntsCommand

fun UntsContext.isUpdatableCommand() =
    this.command in listOf(UntsCommand.CREATE, UntsCommand.UPDATE, UntsCommand.DELETE)
