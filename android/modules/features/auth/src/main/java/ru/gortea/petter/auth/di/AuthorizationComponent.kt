package ru.gortea.petter.auth.di

import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.profile.data.remote.UserUpdateRepository
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.DeviceToken

interface AuthorizationComponent {
    val authorizationRepository: AuthorizationRepository

    val registrationRepository: RegistrationRepository

    val userUpdateRepository: UserUpdateRepository

    @get:DeviceToken
    val deviceTokenRepository: TokenRepository
}
