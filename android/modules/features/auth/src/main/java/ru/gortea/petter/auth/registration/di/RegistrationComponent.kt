package ru.gortea.petter.auth.registration.di

import ru.gortea.petter.auth.data.RegistrationRepository

interface RegistrationComponent {
    val registrationRepository: RegistrationRepository
}
