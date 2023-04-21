package ru.gortea.petter.di.features.auth

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.gortea.petter.auth.AuthController
import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.auth.controller.LoginController
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.auth.data.LogoutRepository
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.DeviceToken
import ru.gortea.petter.token.storage.qualifier.JwtToken
import ru.gortea.petter.token.storage.qualifier.RefreshToken
import javax.inject.Singleton

@Module
class FeatureAuthControllerModule {

    @Singleton
    @Provides
    fun provideAuthController(
        @RefreshToken refreshRepository: TokenRepository,
        @JwtToken jwtRepository: TokenRepository,
        @DeviceToken deviceTokenRepository: TokenRepository,
        logoutRepository: LogoutRepository,
        currentUserRepository: CurrentUserRepository
    ): AuthController {
        return AuthController(
            jwtRepository = jwtRepository,
            refreshRepository = refreshRepository,
            deviceTokenRepository = deviceTokenRepository,
            logoutRepository = logoutRepository,
            userRepository = currentUserRepository
        )
    }
}

@Module
interface FeatureAuthControllerBinder {

    @Binds
    fun bindLoginController(authController: AuthController): LoginController

    @Binds
    fun bindLogoutController(authController: AuthController): LogoutController

    @Binds
    fun bindAuthObservableController(authController: AuthController): AuthObservable
}
