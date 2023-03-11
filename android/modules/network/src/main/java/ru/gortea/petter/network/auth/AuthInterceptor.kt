package ru.gortea.petter.network.auth

import okhttp3.Interceptor
import okhttp3.Response
import ru.gortea.petter.token.storage.JwtTokenRepository

internal class AuthInterceptor(
    private val tokenRepository: JwtTokenRepository
) : Interceptor {
    private var token: String
        get() = tokenRepository.getToken()
        set(value) = tokenRepository.updateToken(value)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (token.isEmpty()) {
            val response = chain.proceed(chain.request())
            token = response.headers[HEADER_KEY] ?: ""
            return response
        }

        val request = chain.request().newBuilder()
            .addHeader(HEADER_KEY, token)
            .build()
        return chain.proceed(request)
    }

    private companion object {
        private const val HEADER_KEY = "AuthorizationToken"
    }
}
