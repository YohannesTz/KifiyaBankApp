package com.github.yohannestz.kifiyabankapp.data.remote.network

import android.annotation.SuppressLint
import android.os.Build
import com.github.yohannestz.kifiyabankapp.BuildConfig
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.getKoin

@SuppressLint("HardwareIds")
val ktorHttpClient = HttpClient(OkHttp) {
    expectSuccess = false

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 60_000
        connectTimeoutMillis = 60_000
        socketTimeoutMillis = 60_000
    }

    install(DefaultRequest) {
        url(BuildConfig.BASE_API_URL)

        val defaultUserAgent = "KifiyaBankApp-Android/${BuildConfig.VERSION_NAME}; Android/${Build.VERSION.RELEASE} (${Build.MODEL}/${Build.MANUFACTURER}; API ${Build.VERSION.SDK_INT})"
        header(HttpHeaders.UserAgent, defaultUserAgent)

        val accessToken = runBlocking { getKoin().get<PreferenceRepository>().accessToken.first() }
        if (accessToken.isNotEmpty()) {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }
    }

    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
    }
}