package ru.gortea.petter.profile.di

import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.GetUserRepository
import ru.gortea.petter.profile.navigation.ProfileExternalNodesProvider

interface ProfileComponent {
    val logoutController: LogoutController

    val getUserRepository: GetUserRepository
    val currentUserRepository: CurrentUserRepository

    val profileNodesProvider: ProfileExternalNodesProvider
}
