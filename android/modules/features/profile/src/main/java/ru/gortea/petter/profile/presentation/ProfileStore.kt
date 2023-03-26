package ru.gortea.petter.profile.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.profile.di.ProfileComponent
import ru.gortea.petter.profile.navigation.ProfileNavTarget
import ru.gortea.petter.profile.presentation.actors.ProfileCheckCurrentUserActor
import ru.gortea.petter.profile.presentation.actors.ProfileGetUserActor
import ru.gortea.petter.profile.presentation.actors.ProfileInitGetUserActor

internal typealias ProfileStore = MviStore<ProfileState, ProfileEvent, Nothing>

internal fun createProfileStore(
    id: String,
    component: ProfileComponent,
    router: PetterRouter<ProfileNavTarget>,
    finish: () -> Unit
): ProfileStore {
    val logoutController = component.logoutController

    val getUserRepository = component.getUserRepository
    val userLocalRepository = component.userLocalRepository

    return TeaStore(
        ProfileState(),
        ProfileReducer(logoutController, router, finish),
        listOf(
            ProfileInitGetUserActor(getUserRepository),
            ProfileGetUserActor(getUserRepository),
            ProfileCheckCurrentUserActor(userLocalRepository)
        ),
        listOf(
            ProfileEvent.InitApi,
            ProfileUiEvent.LoadUser(id)
        )
    )
}