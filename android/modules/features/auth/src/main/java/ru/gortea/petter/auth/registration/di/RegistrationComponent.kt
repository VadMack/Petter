package ru.gortea.petter.auth.registration.di

import ru.gortea.petter.auth.data.AuthorizationByCredentialsRepository
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.profile.data.ProfileUpdateAvatarRepository
import ru.gortea.petter.profile.data.ProfileUpdateRepository

interface RegistrationComponent {
    val registrationRepository: RegistrationRepository
    val authorizationRepository: AuthorizationByCredentialsRepository

    val profileUpdateRepository: ProfileUpdateRepository
    val profileUpdateAvatarRepository: ProfileUpdateAvatarRepository
}
