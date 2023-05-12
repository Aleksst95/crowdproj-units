package com.crowdproj.marketplace.units.app

import com.crowdproj.marketplace.units.api.v1.apiMapper
import com.crowdproj.marketplace.units.api.v1.models.*
import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class WebsocketStubTest {
    @Test
    fun createStub() {
        val request = UnitCreateRequest(
            requestId = "123",
            params = UnitCreateParams(
                alias = "Gram",
                unit = "Kg",
                status = UnitStatus.CONFIRMED,
                conversion = UnitConversionParameters(),
                description = "mass"
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IUnitResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun readStub() {
        val request = UnitReadRequest(
            requestId = "123",
            params = UnitReadParams(
                unitId = "1"
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IUnitResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun updateStub() {
        val request = UnitUpdateRequest(
            requestId = "123",
            params = UnitUpdateParams(
                id = "1",
                alias = "Kilogram",
                unit = "Kg",
                status = UnitStatus.CONFIRMED,
                conversion = UnitConversionParameters(),
                description = "mass"
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IUnitResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun deleteStub() {
        val request = UnitDeleteRequest(
            requestId = "123",
            params = UnitDeleteParams(
                unitId = "1",
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IUnitResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    @Test
    fun searchStub() {
        val request = UnitSearchRequest(
            requestId = "123",
            params = UnitSearchParams(
                unitFilter = UnitSearchFilter(
                    search = "gram"
                )
            ),
            debug = CpBaseDebug(
                mode = CpRequestDebugMode.STUB,
                stub = CpRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IUnitResponse>(request) {
            assertEquals("123", it.requestId)
        }
    }

    private inline fun <reified T> testMethod(
        request: IUnitRequest,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/v1/ws") {
            withTimeout(3000) {
                val income = incoming.receive() as Frame.Text
                val response = apiMapper.decodeFromString<T>(income.readText())
                assertIs<UnitInitResponse>(response)
            }
            send(Frame.Text(apiMapper.encodeToString(request)))
            withTimeout(3000) {
                val income = incoming.receive() as Frame.Text
                val text = income.readText()
                val response = apiMapper.decodeFromString<T>(text)

                assertBlock(response)
            }
        }
    }
}
