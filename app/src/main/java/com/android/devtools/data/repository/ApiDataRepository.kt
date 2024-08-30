package com.android.devtools.data.repository

import androidx.compose.ui.text.AnnotatedString
import com.android.devtools.domain.repository.ApiRepository
import com.android.devtools.domain.usecase.SendRequestUseCase
import com.android.devtools.presentation.support.SpanTextUtil
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import timber.log.Timber
import javax.inject.Inject

class ApiDataRepository @Inject constructor(
    private val client: HttpClient
) : ApiRepository {

    override suspend fun sendRequest(param: SendRequestUseCase.Params): Flow<AnnotatedString> = flow {
        val response = client.get(param.url) {
            url {
                param.list.forEach { p ->
                    parameters.append(p.key, p.value)
                    Timber.d("sendRequest: parameters=${p.key}, value=${p.value}")
                }
            }
        }

        Timber.d("sendRequest: response=${response.bodyAsText()}")
        Timber.d("sendRequest: status=${response.status}")
        response.headers.forEach { s, strings ->
            Timber.d("sendRequest: header=$s, value=$strings")
        }

        val jsonObject: JsonObject = Json.decodeFromString(response.bodyAsText())
        //val model: Map<String, Any?> = Json.decodeFromString(KotlinxGenericMapSerializer, response.bodyAsText())
        val pretty = format.encodeToString(jsonObject)

        Timber.d("sendRequest: pretty response=${pretty}")
        emit(SpanTextUtil().spanJson(pretty))
    }
}

@OptIn(ExperimentalSerializationApi::class)
val format = Json {
    prettyPrint = true
    prettyPrintIndent = "   "
    isLenient = true
    useAlternativeNames = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}