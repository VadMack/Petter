package ru.gortea.petter.token.storage

interface JwtTokenRepository {
    fun updateToken(newValue: String)
    fun getToken(): String
}
