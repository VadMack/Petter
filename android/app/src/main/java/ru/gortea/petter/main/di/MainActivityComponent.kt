package ru.gortea.petter.main.di

import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.profile.data.local.UserLocalRepository

interface MainActivityComponent {
    val authObservable: AuthObservable
    val userLocalRepository: UserLocalRepository
}
