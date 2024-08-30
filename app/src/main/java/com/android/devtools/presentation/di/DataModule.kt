package com.android.devtools.presentation.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    companion object {
        private const val COMMON_TIMEOUT = 15_000L
        const val CONNECT_TIMEOUT = COMMON_TIMEOUT
        const val READ_TIMEOUT = COMMON_TIMEOUT
        const val WRITE_TIMEOUT = COMMON_TIMEOUT
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor = ChuckerInterceptor.Builder(context = context)
        .collector(chuckerCollector)
        .alwaysReadResponseBody(true)
        .build()

    @Provides
    @Singleton
    fun provideChuckerCollector(
        @ApplicationContext context: Context
    ): ChuckerCollector = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_DAY
    )

    @Provides
    @Singleton
    fun provideOkHttpEngine(
        netLogsInterceptor: ChuckerInterceptor
    ) = OkHttp.create {
        addInterceptor(netLogsInterceptor)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        okHttpClient: HttpClientEngine
    ) = HttpClient(okHttpClient) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    prettyPrintIndent = " "
                    isLenient = true
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = COMMON_TIMEOUT
            connectTimeoutMillis = COMMON_TIMEOUT
            socketTimeoutMillis = COMMON_TIMEOUT
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Timber.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("Px-Device-Id", "android")
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            url("https://d-api.sqarb.com/users/")
        }
    }
}