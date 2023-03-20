package ru.gortea.petter.auth.authorization.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.authorization.presentation.actors.AuthUpdateUserActor
import ru.gortea.petter.auth.authorization.presentation.actors.AuthValidateActor
import ru.gortea.petter.auth.authorization.presentation.actors.AuthorizeActor
import ru.gortea.petter.auth.authorization.presentation.actors.AuthorizeInitActor
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.graph.NavTarget

internal typealias AuthStore = MviStore<AuthState, AuthEvent, Nothing>

internal fun createAuthStore(
    component: AuthorizationComponent,
    router: PetterRouter<NavTarget>
): AuthStore {
    val authRepo = component.authorizationRepository

    return TeaStore(
        initialState = AuthState(),
        reducer = AuthReducer(router),
        actors = listOf(
            AuthorizeInitActor(authRepo),
            AuthorizeActor(authRepo),
            AuthUpdateUserActor(component.userLocalRepository),
            AuthValidateActor()
        ),
        initialEvents = listOf(AuthEvent.InitApi)
    )
}
