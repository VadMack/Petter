package ru.gortea.petter.profile.edit.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.android.util.validation.ValidatorComposite
import ru.gortea.petter.profile.edit.presentation.ProfileEditCommand
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent
import ru.gortea.petter.profile.edit.presentation.ProfileEditState
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason

internal class ProfileEditValidateActor(
    private val validator: ValidatorComposite<ProfileEditState, ProfileEditFailedReason>
) : Actor<ProfileEditCommand, ProfileEditEvent> {

    override fun process(commands: Flow<ProfileEditCommand>): Flow<ProfileEditEvent> {
        return commands.filterIsInstance<ProfileEditCommand.Validate>()
            .mapLatest { validator.validateWithReasons(it.state) }
            .map { ProfileEditEvent.Validated(it) }
    }
}
