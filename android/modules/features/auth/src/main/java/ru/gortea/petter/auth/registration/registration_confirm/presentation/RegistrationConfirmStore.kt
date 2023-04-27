package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.AuthorizeActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.InitAuthorizeActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.InitRegistrationConfirmActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.InitResendCodeActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationConfirmActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationConfirmValidateActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.ResendCodeActor
import ru.gortea.petter.navigation.Router

internal typealias RegistrationConfirmStore = MviStore<RegistrationConfirmState, RegistrationConfirmEvent>

internal fun createRegistrationConfirmStore(
    component: AuthorizationComponent,
    email: String,
    userId: String,
    username: String,
    password: String,
    router: Router<AuthorizationNavTarget>,
    finish: () -> Unit
): RegistrationConfirmStore {
    val repo = component.registrationRepository
    val authRepo = component.authorizationRepository

    return TeaStore(
        RegistrationConfirmState(
            userId = userId,
            email = email,
            username = username,
            password = password
        ),
        RegistrationConfirmReducer(finish, router),
        listOf(
            InitAuthorizeActor(authRepo),
            InitRegistrationConfirmActor(repo),
            InitResendCodeActor(repo),
            AuthorizeActor(authRepo, component.deviceTokenRepository),
            RegistrationConfirmValidateActor(),
            RegistrationConfirmActor(repo),
            ResendCodeActor(repo)
        ),
        listOf(
            RegistrationConfirmEvent.InitApi
        )
    )
}

