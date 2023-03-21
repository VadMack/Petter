package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.registration.registration_form.presentation.actors.RegistrationCreateAccountActor
import ru.gortea.petter.auth.registration.registration_form.presentation.actors.RegistrationInitCreateAccountActor
import ru.gortea.petter.auth.registration.registration_form.presentation.actors.RegistrationValidateActor
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.RegistrationValidatorComposite
import ru.gortea.petter.navigation.Router

internal typealias RegistrationStore = MviStore<RegistrationState, RegistrationEvent, Nothing>

internal fun createRegistrationStore(
    component: AuthorizationComponent,
    router: Router<AuthorizationNavTarget>
): RegistrationStore {
    val repo = component.registrationRepository

    return TeaStore(
        RegistrationState(),
        RegistrationReducer(router),
        listOf(
            RegistrationValidateActor(RegistrationValidatorComposite()),
            RegistrationCreateAccountActor(repo),
            RegistrationInitCreateAccountActor(repo)
        ),
        listOf(RegistrationEvent.InitApi)
    )
}
