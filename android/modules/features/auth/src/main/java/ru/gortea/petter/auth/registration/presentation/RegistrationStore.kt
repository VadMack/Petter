package ru.gortea.petter.auth.registration.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.registration.di.RegistrationComponent
import ru.gortea.petter.auth.registration.presentation.actors.RegistrationCreateAccountActor
import ru.gortea.petter.auth.registration.presentation.actors.RegistrationValidateActor
import ru.gortea.petter.auth.registration.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.presentation.validation.RegistrationValidatorComposite

internal typealias RegistrationStore = MviStore<RegistrationState, RegistrationEvent, Nothing>

internal fun createRegistrationStore(
    component: RegistrationComponent
): RegistrationStore = TeaStore(
    RegistrationState(),
    RegistrationReducer(),
    listOf(
        RegistrationValidateActor(RegistrationValidatorComposite()),
        RegistrationCreateAccountActor(component.registrationRepository)
    )
)
