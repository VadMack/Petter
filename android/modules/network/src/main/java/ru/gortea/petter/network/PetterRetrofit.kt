package ru.gortea.petter.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object PetterRetrofit {

    fun create(): Retrofit {
        val contentType = "*/*".toMediaType()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    private fun String.toMediaType(): MediaType {
        return MediaType.get(this)
    }
}
