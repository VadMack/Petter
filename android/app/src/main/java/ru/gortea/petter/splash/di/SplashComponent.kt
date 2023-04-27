package ru.gortea.petter.splash.di

import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.DeviceToken

interface SplashComponent {

    val authRepository: AuthorizationRepository

    @get:DeviceToken
    val tokenRepository: TokenRepository
}
