package ru.gortea.petter.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.gortea.petter.auth.controller.LoginController
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.auth.controller.model.AuthorizedUserModel
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.data.model.TokenAuthorizationModel
import ru.gortea.petter.data.MapSourceRepository
import ru.gortea.petter.network.errors.PetterNetworkError.RequestError
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.token.storage.TokenRepository


class AuthorizationRepository(
    private val api: AuthApi,
    private val refreshTokenRepository: TokenRepository,
    private val deviceTokenRepository: TokenRepository,
    private val loginController: LoginController,
    private val logoutController: LogoutController
) : MapSourceRepository<AuthorizedUserModel, UserModel>(
    source = {
        if (it is CredsAuthorizationModel) {
            api.auth(it)
        } else {
            api.auth(
                TokenAuthorizationModel(
                    refreshToken = refreshTokenRepository.getToken(),
                    deviceToken = deviceTokenRepository.getToken()
                )
            )
        }
    },
    mapper = {
        loginController.login(it)
        it.user
    },
    onFailed = { reason ->
        if (reason is RequestError && refreshTokenRepository.getToken().isNotEmpty()) {
            loginController.offlineLogin()
        } else {
            logoutController.logout()
        }
    },
    coroutineScope = CoroutineScope(Dispatchers.IO)
)
