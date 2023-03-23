package ru.gortea.petter.profile.edit.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.profile.data.remote.UserUpdateRepository
import ru.gortea.petter.profile.edit.presentation.FillAccountCommand
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent

internal class ProfileEditUpdateUserActor(
    private val repository: UserUpdateRepository
) : Actor<FillAccountCommand, ProfileEditEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<ProfileEditEvent> {
        return commands.filterIsInstance<FillAccountCommand.UpdateUser>()
            .mapLatest { repository.invalidate(it.user) }
            .flatMapMerge { emptyFlow() }
    }
}
