package ru.gortea.petter.auth.stubs

import ru.gortea.petter.auth.controller.LoginController
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.auth.controller.model.AuthorizedUserModel
import ru.gortea.petter.token.storage.TokenRepository

internal class LoginLogoutControllerStub(
    private val refreshTokenRepo: TokenRepository
) : LoginController, LogoutController {

    override fun login(userModel: AuthorizedUserModel) {
        refreshTokenRepo.updateToken(userModel.refreshToken)
    }

    override fun offlineLogin() = Unit

    override fun logout() {
        refreshTokenRepo.removeToken()
    }
}
