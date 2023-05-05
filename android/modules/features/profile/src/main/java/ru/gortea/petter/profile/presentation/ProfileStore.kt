package ru.gortea.petter.profile.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.di.ProfileComponent
import ru.gortea.petter.profile.navigation.ProfileNavTarget
import ru.gortea.petter.profile.presentation.actors.ProfileCheckCurrentUserActor
import ru.gortea.petter.profile.presentation.actors.ProfileInitLoadUserActor
import ru.gortea.petter.profile.presentation.actors.ProfileLoadUserActor

internal typealias ProfileStore = MviStore<ProfileState, ProfileEvent>

internal fun createProfileStore(
    id: String,
    component: ProfileComponent,
    router: Router<ProfileNavTarget>,
    finish: () -> Unit
): ProfileStore {
    val logoutController = component.logoutController

    val getUserRepository = component.getUserRepository
    val userLocalRepository = component.currentUserRepository

    return TeaStore(
        ProfileState(),
        ProfileReducer(logoutController, router, finish),
        listOf(
            ProfileInitLoadUserActor(getUserRepository),
            ProfileLoadUserActor(getUserRepository),
            ProfileCheckCurrentUserActor(userLocalRepository)
        ),
        listOf(
            ProfileEvent.InitApi,
            ProfileUiEvent.LoadUser(id)
        )
    )
}
