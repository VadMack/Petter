package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.registration.di.RegistrationComponent
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationConfirmActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationConfirmValidateActor
import ru.gortea.petter.auth.registration.registration_confirm.presentation.actors.RegistrationRetryConfirmActor

internal typealias RegistrationConfirmStore = MviStore<RegistrationConfirmState, RegistrationConfirmEvent, Nothing>

internal fun createRegistrationConfirmStore(
    component: RegistrationComponent,
    email: String,
    userId: String
): RegistrationConfirmStore {
    val repo = component.registrationRepository

    return TeaStore(
        RegistrationConfirmState(userId = userId, email = email),
        RegistrationConfirmReducer(),
        listOf(
            RegistrationConfirmValidateActor(),
            RegistrationConfirmActor(repo),
            RegistrationRetryConfirmActor(repo)
        )
    )
}

