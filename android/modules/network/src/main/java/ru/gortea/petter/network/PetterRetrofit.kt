package ru.gortea.petter.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.gortea.petter.network.auth.AuthInterceptor
import ru.gortea.petter.network.errors.ErrorHandlingCallAdapterFactory
import ru.gortea.petter.token.storage.JwtTokenRepository

object PetterRetrofit {

    fun create(tokenRepository: JwtTokenRepository): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(createClient(tokenRepository))
            .addConverterFactory(Json.asConverterFactory(contentType))
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory())
            .build()
    }

    private fun createClient(tokenRepository: JwtTokenRepository): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = AuthInterceptor(tokenRepository)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }
}
