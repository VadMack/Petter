package ru.gortea.petter.auth.registration.fill_account.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountInitUpdateUserActor
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountUpdateUserActor
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountValidateActor
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.FillAccountValidatorComposite

internal typealias FillAccountStore = MviStore<FillAccountState, FillAccountEvent, FillAccountAction>

internal fun createFillAccountStore(
    component: AuthorizationComponent,
    finish: () -> Unit
): FillAccountStore {
    val userUpdateRepo = component.userUpdateRepository

    return TeaStore(
        FillAccountState(),
        FillAccountReducer(finish),
        listOf(
            FillAccountInitUpdateUserActor(userUpdateRepo),
            FillAccountUpdateUserActor(userUpdateRepo),
            FillAccountValidateActor(FillAccountValidatorComposite())
        ),
        listOf(
            FillAccountEvent.InitApi
        )
    )
}
