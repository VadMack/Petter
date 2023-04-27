package ru.gortea.petter.main.di

import coil.ImageLoader
import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.profile.data.local.CurrentUserRepository

interface MainActivityComponent {
    val authObservable: AuthObservable
    val currentUserRepository: CurrentUserRepository
    val imageLoader: ImageLoader
}
