package ru.gortea.petter.network.auth

import okhttp3.Interceptor
import okhttp3.Response
import ru.gortea.petter.token.storage.TokenRepository

internal class AuthInterceptor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    private var token: String
        get() = tokenRepository.getToken()
        set(value) = tokenRepository.updateToken(value)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER_KEY, "Bearer $token")
            .build()

        println("xxx: $request")

        val response = chain.proceed(request)
        response.headers[HEADER_KEY]?.let(::token::set)
        return response
    }

    private companion object {
        private const val HEADER_KEY = "Authorization"
    }
}
