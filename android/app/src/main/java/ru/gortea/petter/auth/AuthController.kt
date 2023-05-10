package ru.gortea.petter.auth

import com.google.firebase.analytics.FirebaseAnalytics
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
    private val userRepository: CurrentUserRepository,
    private val analytics: FirebaseAnalytics
) : AuthObservable, LoginController, LogoutController {
    private val authorizedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun login(userModel: AuthorizedUserModel) {
        coroutineScope.launch {
            refreshRepository.updateToken(userModel.refreshToken)
            userRepository.updateCurrentUser(userModel.user)
            analytics.setUserId(userModel.user.id)
            authorizedFlow.emit(true)
        }
    }

    override fun offlineLogin() {
        coroutineScope.launch {
            analytics.setUserId(userRepository.getCurrentUser().id)
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
            analytics.setUserId(null)
        }
    }

    override fun isAuthorized(): Flow<Boolean> = authorizedFlow
}
