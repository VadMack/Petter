package ru.gortea.petter.token.storage

interface TokenRepository {
    fun updateToken(newValue: String)
    fun getToken(): String
    fun removeToken()
}
