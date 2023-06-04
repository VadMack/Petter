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
import ru.gortea.petter.network.url.BASE_URL
import ru.gortea.petter.token.storage.TokenRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object PetterNetwork {

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        serializersModule = SerializersModule {
            contextual(LocalDate::class, LocalDateSerializer)
            contextual(LocalDateTime::class, LocalDateTimeSerializer)
        }
    }

    fun create(client: OkHttpClient, logoutController: LogoutController): Retrofit {
        return setupBuilder(client)
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory(logoutController))
            .build()
    }

    fun create(client: OkHttpClient): Retrofit {
        return setupBuilder(client).build()
    }

    private fun setupBuilder(client: OkHttpClient): Retrofit.Builder {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("http://$BASE_URL/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
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
