package ru.gortea.petter.auth.registration.fill_account.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountInitUpdateActor
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountInitUploadAvatarActor
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountUpdateActor
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountUploadAvatarActor
import ru.gortea.petter.auth.registration.fill_account.presentation.actors.FillAccountValidateActor
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.FillAccountValidatorComposite

internal typealias FillAccountStore = MviStore<FillAccountState, FillAccountEvent, FillAccountAction>

internal fun createFillAccountStore(
    component: AuthorizationComponent
): FillAccountStore {
    val profileUpdateRepo = component.profileUpdateRepository
    val profileUpdateAvatarRepo = component.profileUpdateAvatarRepository

    return TeaStore(
        FillAccountState(),
        FillAccountReducer(),
        listOf(
            FillAccountInitUpdateActor(profileUpdateRepo),
            FillAccountInitUploadAvatarActor(profileUpdateAvatarRepo),
            FillAccountUpdateActor(profileUpdateRepo),
            FillAccountUploadAvatarActor(profileUpdateAvatarRepo),
            FillAccountValidateActor(FillAccountValidatorComposite())
        )
    )
}
