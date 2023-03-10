package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.registration.di.RegistrationComponent
import ru.gortea.petter.auth.registration.registration_form.presentation.actors.RegistrationCreateAccountActor
import ru.gortea.petter.auth.registration.registration_form.presentation.actors.RegistrationRetryCreateAccountActor
import ru.gortea.petter.auth.registration.registration_form.presentation.actors.RegistrationValidateActor
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.RegistrationValidatorComposite

internal typealias RegistrationStore = MviStore<RegistrationState, RegistrationEvent, Nothing>

internal fun createRegistrationStore(
    component: RegistrationComponent
): RegistrationStore {
    val repo = component.registrationRepository

    return TeaStore(
        RegistrationState(),
        RegistrationReducer(),
        listOf(
            RegistrationValidateActor(RegistrationValidatorComposite()),
            RegistrationCreateAccountActor(repo),
            RegistrationRetryCreateAccountActor(repo)
        )
    )
}
