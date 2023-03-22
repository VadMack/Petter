package ru.gortea.petter.splash.di

import ru.gortea.petter.auth.data.AuthorizationRepository

interface SplashComponent {

    val authRepository: AuthorizationRepository
}
