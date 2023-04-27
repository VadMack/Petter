package ru.gortea.petter.auth.data

import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.LogoutModel
import ru.gortea.petter.token.storage.TokenRepository

class LogoutRepository(
    private val api: AuthApi,
    private val deviceTokenRepository: TokenRepository
) {
    suspend fun logout() {
        val model = LogoutModel(deviceTokenRepository.getToken())
        try {
            api.logout(model)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
