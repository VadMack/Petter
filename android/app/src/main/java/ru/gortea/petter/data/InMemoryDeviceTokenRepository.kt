package ru.gortea.petter.data

import ru.gortea.petter.token.storage.TokenRepository

class InMemoryDeviceTokenRepository : TokenRepository {
    private var token: String = ""

    override fun updateToken(newValue: String) {
        token = newValue
    }

    override fun getToken(): String = token

    override fun removeToken() = Unit
}
