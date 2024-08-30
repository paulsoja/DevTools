package com.android.devtools.domain.api

import com.android.devtools.domain.api.config.RequestConfigMarker
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Base64

@Serializable
data class RequestParam(
    val key: String,
    val value: String
) : RequestConfigMarker {

    companion object{
        val empty = RequestParam("", "")
    }
}

fun List<RequestParam>.toStringParameter(): String {
    val json = Json.encodeToString(this)
    val encodedValue = Base64.getUrlEncoder().encodeToString(json.toByteArray(Charsets.UTF_8))
    return encodedValue
}

inline fun <reified T> String.toModel(default: T): T {
    return try {
        val decodedBytes = Base64.getUrlDecoder().decode(this)
        val decodedString = String(decodedBytes, Charsets.UTF_8)
        Json.decodeFromString<T>(decodedString)
    } catch (e: Exception) {
        default
    }
}
