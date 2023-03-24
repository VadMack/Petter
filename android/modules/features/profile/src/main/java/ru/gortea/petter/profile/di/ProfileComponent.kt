package ru.gortea.petter.profile.di

import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.profile.data.remote.GetUserRepository

interface ProfileComponent {
    val logoutController: LogoutController

    val getUserRepository: GetUserRepository
    val userLocalRepository: UserLocalRepository
}
