package ru.gortea.petter.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.network.auth.AuthInterceptor
import ru.gortea.petter.network.errors.ErrorHandlingCallAdapterFactory
import ru.gortea.petter.network.serialization.LocalDateSerializer
import ru.gortea.petter.network.serialization.LocalDateTimeSerializer
import ru.gortea.petter.token.storage.TokenRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object PetterNetwork {

    const val BASE_URL = "10.0.2.2:8080"

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        serializersModule = SerializersModule {
            contextual(LocalDate::class, LocalDateSerializer)
            contextual(LocalDateTime::class, LocalDateTimeSerializer)
        }
    }

    fun create(client: OkHttpClient, logoutController: LogoutController): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("http://$BASE_URL/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory(logoutController))
            .build()
    }

    fun create(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("http://$BASE_URL/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    fun createClient(tokenRepository: TokenRepository): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = AuthInterceptor(tokenRepository)

        return OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }
}
