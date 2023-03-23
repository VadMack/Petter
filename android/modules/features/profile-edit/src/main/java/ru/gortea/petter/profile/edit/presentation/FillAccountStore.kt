package ru.gortea.petter.profile.edit.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.profile.edit.di.ProfileEditComponent
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditGetCurrentUser
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditInitUpdateUserActor
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditUpdateUserActor
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditValidateActor
import ru.gortea.petter.profile.edit.presentation.validation.ProfileEditValidatorComposite

internal typealias ProfileEditStore = MviStore<ProfileEditState, ProfileEditEvent, ProfileEditAction>

internal fun createProfileEditStore(
    component: ProfileEditComponent,
    finish: () -> Unit
): ProfileEditStore {
    val userUpdateRepo = component.userUpdateRepository
    val localUserRepo = component.userLocalRepository

    return TeaStore(
        ProfileEditState(),
        ProfileEditReducer(finish),
        listOf(
            ProfileEditInitUpdateUserActor(userUpdateRepo),
            ProfileEditUpdateUserActor(userUpdateRepo),
            ProfileEditValidateActor(ProfileEditValidatorComposite()),
            ProfileEditGetCurrentUser(localUserRepo)
        ),
        listOf(
            ProfileEditEvent.InitApi,
            ProfileEditEvent.GetLocalUser
        )
    )
}
