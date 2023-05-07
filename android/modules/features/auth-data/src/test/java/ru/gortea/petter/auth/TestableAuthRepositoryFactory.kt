package ru.gortea.petter.auth

import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.auth.stubs.AuthApiStub
import ru.gortea.petter.auth.stubs.FailedAuthApiStub
import ru.gortea.petter.auth.stubs.LoginLogoutControllerStub
import ru.gortea.petter.token.storage.TokenRepository

internal object TestableAuthRepositoryFactory {

    fun createAuthRepository(tokenRepository: TokenRepository): AuthorizationRepository {
        return AuthorizationRepository(
            api = AuthApiStub,
            refreshTokenRepository = tokenRepository,
            deviceTokenRepository = tokenRepository,
            loginController = LoginLogoutControllerStub(tokenRepository),
            logoutController = LoginLogoutControllerStub(tokenRepository)
        )
    }

    fun createFailedAuthRepository(tokenRepository: TokenRepository): AuthorizationRepository {
        return AuthorizationRepository(
            api = FailedAuthApiStub,
            refreshTokenRepository = tokenRepository,
            deviceTokenRepository = tokenRepository,
            loginController = LoginLogoutControllerStub(tokenRepository),
            logoutController = LoginLogoutControllerStub(tokenRepository)
        )
    }
}
