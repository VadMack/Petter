package ru.gortea.petter.profile.edit.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.edit.di.ProfileEditComponent
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditGetCurrentUser
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditInitUpdateUserActor
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditUpdateUserActor
import ru.gortea.petter.profile.edit.presentation.actors.ProfileEditValidateActor
import ru.gortea.petter.profile.edit.presentation.analytics.ProfileEditAnalyticsHandler
import ru.gortea.petter.profile.edit.presentation.validation.ProfileEditValidatorComposite

internal typealias ProfileEditStore = MviStore<ProfileEditState, ProfileEditEvent>

internal fun createProfileEditStore(
    component: ProfileEditComponent,
    router: Router<*>,
    showModalImageChooser: () -> Unit,
    showImagePicker: () -> Unit,
    finish: () -> Unit
): ProfileEditStore {
    val userUpdateRepo = component.userUpdateRepository
    val localUserRepo = component.currentUserRepository

    return TeaStore(
        initialState = ProfileEditState(),
        reducer = ProfileEditReducer(router, showModalImageChooser, showImagePicker, finish),
        actors = listOf(
            ProfileEditInitUpdateUserActor(userUpdateRepo),
            ProfileEditUpdateUserActor(userUpdateRepo),
            ProfileEditValidateActor(ProfileEditValidatorComposite()),
            ProfileEditGetCurrentUser(localUserRepo)
        ),
        initialEvents = listOf(
            ProfileEditEvent.InitApi,
            ProfileEditEvent.GetLocalUser
        ),
        analyticsHandler = ProfileEditAnalyticsHandler(component.profileEditAnalyticsController)
    )
}
