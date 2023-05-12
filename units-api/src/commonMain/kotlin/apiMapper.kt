package com.crowdproj.marketplace.units.api.v1

import com.crowdproj.marketplace.units.api.v1.models.*
import com.crowdproj.marketplace.units.api.v1.requests.IUnitRequestStrategy
import com.crowdproj.marketplace.units.api.v1.responses.IUnitResponseStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

@OptIn(ExperimentalSerializationApi::class)
val apiMapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IUnitRequest::class) {
            val strategy = IUnitRequestStrategy.membersByClazz[it::class] ?: return@polymorphicDefaultSerializer null
            @Suppress("UNCHECKED_CAST")
            RequestSerializer(strategy.serializer) as SerializationStrategy<IUnitRequest>
        }

        polymorphicDefaultDeserializer(IUnitRequest::class ) {
            UnitRequestSerializer
        }

        polymorphicDefaultSerializer(IUnitResponse::class) {
            val strategy = IUnitResponseStrategy.membersByClazz[it::class] ?: return@polymorphicDefaultSerializer null
            @Suppress("UNCHECKED_CAST")
            ResponseSerializer(strategy.serializer) as SerializationStrategy<IUnitResponse>
        }

        polymorphicDefaultDeserializer(IUnitResponse::class) {
            UnitResponseSerializer
        }

        contextual(UnitRequestSerializer)
        contextual(UnitResponseSerializer)
    }
}

fun Json.encodeResponse(response: IUnitResponse): String = encodeToString(UnitResponseSerializer, response)
