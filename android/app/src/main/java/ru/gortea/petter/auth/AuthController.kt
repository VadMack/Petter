package ru.gortea.petter.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.auth.controller.LoginController
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.auth.controller.model.AuthorizedUserModel
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.token.storage.TokenRepository

class AuthController(
    private val jwtRepository: TokenRepository,
    private val refreshRepository: TokenRepository,
    private val userRepository: UserLocalRepository
) : AuthObservable, LoginController, LogoutController {
    private val authorizedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override suspend fun login(userModel: AuthorizedUserModel) {
        refreshRepository.updateToken(userModel.refreshToken)
        userRepository.updateCurrentUser(userModel.user)
        authorizedFlow.emit(true)
    }

    override suspend fun logout() {
        jwtRepository.removeToken()
        refreshRepository.removeToken()
        userRepository.deleteCurrentUser()
        authorizedFlow.emit(false)
    }

    override fun isAuthorized(): StateFlow<Boolean> = authorizedFlow.asStateFlow()
}
