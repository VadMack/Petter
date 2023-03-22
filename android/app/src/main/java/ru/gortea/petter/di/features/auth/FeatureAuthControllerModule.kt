package ru.gortea.petter.di.features.auth

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.gortea.petter.auth.AuthController
import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.auth.controller.LoginController
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.di.token.qualifier.JwtToken
import ru.gortea.petter.di.token.qualifier.RefreshToken
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.token.storage.TokenRepository
import javax.inject.Singleton

@Module
class FeatureAuthControllerModule {

    @Singleton
    @Provides
    fun provideAuthController(
        @RefreshToken refreshRepository: TokenRepository,
        @JwtToken jwtRepository: TokenRepository,
        userLocalRepository: UserLocalRepository
    ): AuthController {
        return AuthController(
            jwtRepository = jwtRepository,
            refreshRepository = refreshRepository,
            userRepository = userLocalRepository
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
