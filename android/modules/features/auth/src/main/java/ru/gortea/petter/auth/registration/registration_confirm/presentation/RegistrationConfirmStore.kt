package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.registration.di.RegistrationComponent
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.*
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationConfirmActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationConfirmValidateActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationRetryConfirmActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.ResendCodeActor

internal typealias RegistrationConfirmStore = MviStore<RegistrationConfirmState, RegistrationConfirmEvent, Nothing>

internal fun createRegistrationConfirmStore(
    component: RegistrationComponent,
    email: String,
    userId: String,
    username: String,
    password: String
): RegistrationConfirmStore {
    val repo = component.registrationRepository

    return TeaStore(
        RegistrationConfirmState(
            userId = userId,
            email = email,
            username = username,
            password = password
        ),
        RegistrationConfirmReducer(),
        listOf(
            AuthorizeActor(component.authorizationRepository),
            RegistrationConfirmValidateActor(),
            RegistrationConfirmActor(repo),
            RegistrationRetryConfirmActor(repo),
            ResendCodeActor(repo),
            RetryResendCodeStatus(repo)
        )
    )
}

