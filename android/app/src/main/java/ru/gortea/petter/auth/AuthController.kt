package ru.gortea.petter.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.auth.controller.LoginController
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.auth.controller.model.AuthorizedUserModel
import ru.gortea.petter.auth.data.LogoutRepository
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.token.storage.TokenRepository

class AuthController(
    private val jwtRepository: TokenRepository,
    private val refreshRepository: TokenRepository,
    private val deviceTokenRepository: TokenRepository,
    private val logoutRepository: LogoutRepository,
    private val userRepository: CurrentUserRepository
) : AuthObservable, LoginController, LogoutController {
    private val authorizedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun login(userModel: AuthorizedUserModel) {
        coroutineScope.launch {
            refreshRepository.updateToken(userModel.refreshToken)
            userRepository.updateCurrentUser(userModel.user)
            authorizedFlow.emit(true)
        }
    }

    override fun logout() {
        coroutineScope.launch {
            logoutRepository.logout()
            deviceTokenRepository.removeToken()
            authorizedFlow.emit(false)
            jwtRepository.removeToken()
            refreshRepository.removeToken()
            userRepository.deleteCurrentUser()
        }
    }

    override fun isAuthorized(): Flow<Boolean> = authorizedFlow
}
