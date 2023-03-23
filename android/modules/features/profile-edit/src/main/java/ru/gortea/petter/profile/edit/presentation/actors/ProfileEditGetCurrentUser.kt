package ru.gortea.petter.profile.edit.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.profile.edit.presentation.FillAccountCommand
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent

internal class ProfileEditGetCurrentUser(
    private val userLocalRepository: UserLocalRepository
) : Actor<FillAccountCommand, ProfileEditEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<ProfileEditEvent> {
        return commands.filterIsInstance<FillAccountCommand.GetUserLocalData>()
            .mapLatest { userLocalRepository.getCurrentUser() }
            .filterNotNull()
            .map { ProfileEditEvent.LocalUser(it) }
    }
}
