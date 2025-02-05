package com.crowdproj.marketplace.units.common.models

data class UntsConversionParameters(
    var A: Double = 1.0,
    var B: Double = 0.0,
) {
    companion object {
        val NONE = UntsConversionParameters(A = 0.0, B = 0.0)
    }
}
