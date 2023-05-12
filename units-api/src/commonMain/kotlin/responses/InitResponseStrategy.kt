package com.crowdproj.marketplace.units.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.marketplace.units.api.v1.models.IUnitResponse
import com.crowdproj.marketplace.units.api.v1.models.UnitInitResponse
import kotlin.reflect.KClass

object InitResponseStrategy: IUnitResponseStrategy {
    override val discriminator: String = "init"
    override val clazz: KClass<out IUnitResponse> = UnitInitResponse::class
    override val serializer: KSerializer<out IUnitResponse> = UnitInitResponse.serializer()
    override fun <T : IUnitResponse> fillDiscriminator(req: T): T {
        require(req is UnitInitResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
