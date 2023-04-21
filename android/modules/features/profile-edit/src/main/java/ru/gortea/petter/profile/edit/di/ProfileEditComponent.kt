package ru.gortea.petter.profile.edit.di

import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.UserUpdateRepository

interface ProfileEditComponent {
    val userUpdateRepository: UserUpdateRepository
    val currentUserRepository: CurrentUserRepository
}
