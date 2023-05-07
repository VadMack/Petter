package ru.gortea.petter.auth.stubs

import ru.gortea.petter.token.storage.TokenRepository

internal class TokenRepositoryStub : TokenRepository {
    private var token: String = ""

    override fun updateToken(newValue: String) {
        token = newValue
    }

    override fun getToken(): String = token

    override fun removeToken() {
        token = ""
    }
}
