package com.crowdproj.marketplace.units.app.jvm

import com.crowdproj.marketplace.units.api.v1.apiMapper
import com.crowdproj.marketplace.units.api.v1.models.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import kotlin.test.assertEquals

class V1UnitStubApiTest {
    @Test
    fun create() = testApplication {
        val response = client.post("/v1/unit/create") {
            val requestObj = UnitCreateRequest(
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
                ),
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<UnitCreateResponse>(responseJson)

        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.unit.id)
        assertEquals("Kilogram", responseObj.unit.alias)
        assertEquals("Kg", responseObj.unit.unit)
        assertEquals("Unit of mass", responseObj.unit.description)
        assertEquals(UnitStatus.CONFIRMED, responseObj.unit.status)
    }

    @Test
    fun read() = testApplication {
        val response = client.post("/v1/unit/read") {
            val requestObj = UnitReadRequest(
                requestId = "123",
                params = UnitReadParams(
                    unitId = "1"
                ),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                ),
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<UnitReadResponse>(responseJson)

        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.unit.id)
        assertEquals("Kilogram", responseObj.unit.alias)
        assertEquals("Kg", responseObj.unit.unit)
        assertEquals("Unit of mass", responseObj.unit.description)
        assertEquals(UnitStatus.CONFIRMED, responseObj.unit.status)
    }

    @Test
    fun update() = testApplication {
        val response = client.post("/v1/unit/update") {
            val requestObj = UnitUpdateRequest(
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
                ),
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<UnitUpdateResponse>(responseJson)

        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.unit.id)
        assertEquals("Kilogram", responseObj.unit.alias)
        assertEquals("Kg", responseObj.unit.unit)
        assertEquals("Unit of mass", responseObj.unit.description)
        assertEquals(UnitStatus.CONFIRMED, responseObj.unit.status)
    }

    @Test
    fun delete() = testApplication {
        val response = client.post("/v1/unit/delete") {
            val requestObj = UnitDeleteRequest(
                requestId = "123",
                params = UnitDeleteParams(
                    unitId = "1"
                ),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                ),
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }

        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<UnitDeleteResponse>(responseJson)

        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.unit.id)
        assertEquals("Kilogram", responseObj.unit.alias)
        assertEquals("Kg", responseObj.unit.unit)
        assertEquals("Unit of mass", responseObj.unit.description)
        assertEquals(UnitStatus.DELETED, responseObj.unit.status)
    }

    @Test
    fun search() = testApplication {
        val response = client.post("/v1/unit/search") {
            val requestObj = UnitSearchRequest(
                requestId = "123",
                params = UnitSearchParams(
                    unitFilter = UnitSearchFilter(
                        search = "gram"
                    )
                ),
                debug = CpBaseDebug(
                    mode = CpRequestDebugMode.STUB,
                    stub = CpRequestDebugStubs.SUCCESS
                ),
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiMapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiMapper.decodeFromString<UnitSearchResponse>(responseJson)

        assertEquals(200, response.status.value)
        assertEquals(2, responseObj.units.size)
        assertEquals("2", responseObj.units.last().id)
        assertEquals("gram", responseObj.units.last().alias)
    }
}
