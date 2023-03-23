package ru.gortea.petter.profile.edit.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.profile.data.remote.UserUpdateRepository
import ru.gortea.petter.profile.edit.presentation.FillAccountCommand
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent

internal class ProfileEditInitUpdateUserActor(
    private val repository: UserUpdateRepository
) : Actor<FillAccountCommand, ProfileEditEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<ProfileEditEvent> {
        return commands.filterIsInstance<FillAccountCommand.InitUpdateUser>()
            .flatMapLatest { repository.get() }
            .map { ProfileEditEvent.UserUpdateStatus(it) }
    }
}
