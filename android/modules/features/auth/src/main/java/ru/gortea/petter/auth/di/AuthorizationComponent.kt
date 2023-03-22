package ru.gortea.petter.auth.di

import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.profile.data.remote.ProfileUpdateAvatarRepository
import ru.gortea.petter.profile.data.remote.ProfileUpdateRepository

interface AuthorizationComponent {
    val authorizationRepository: AuthorizationRepository

    val registrationRepository: RegistrationRepository

    val profileUpdateRepository: ProfileUpdateRepository
    val profileUpdateAvatarRepository: ProfileUpdateAvatarRepository
}
