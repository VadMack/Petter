package ru.gortea.petter.profile.edit.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.android.util.validation.ValidatorComposite
import ru.gortea.petter.profile.edit.presentation.FillAccountCommand
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent
import ru.gortea.petter.profile.edit.presentation.ProfileEditState
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason

internal class ProfileEditValidateActor(
    private val validator: ValidatorComposite<ProfileEditState, ProfileEditFailedReason>
) : Actor<FillAccountCommand, ProfileEditEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<ProfileEditEvent> {
        return commands.filterIsInstance<FillAccountCommand.Validate>()
            .mapLatest { validator.validateWithReasons(it.state) }
            .map { ProfileEditEvent.Validated(it) }
    }
}
