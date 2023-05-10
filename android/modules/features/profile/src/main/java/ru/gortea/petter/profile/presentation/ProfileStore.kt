package ru.gortea.petter.profile.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.di.ProfileComponent
import ru.gortea.petter.profile.navigation.ProfileNavTarget
import ru.gortea.petter.profile.presentation.actors.ProfileCheckCurrentUserActor
import ru.gortea.petter.profile.presentation.actors.ProfileInitLoadUserActor
import ru.gortea.petter.profile.presentation.actors.ProfileLoadUserActor
import ru.gortea.petter.profile.presentation.analytics.ProfileAnalyticsHandler

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
        initialState = ProfileState(),
        reducer = ProfileReducer(logoutController, router, finish),
        actors = listOf(
            ProfileInitLoadUserActor(getUserRepository),
            ProfileLoadUserActor(getUserRepository),
            ProfileCheckCurrentUserActor(userLocalRepository)
        ),
        initialEvents = listOf(
            ProfileEvent.InitApi,
            ProfileUiEvent.LoadUser(id)
        ),
        analyticsHandler = ProfileAnalyticsHandler(component.profileAnalyticsController)
    )
}
