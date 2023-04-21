package ru.gortea.petter.profile.edit.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.edit.presentation.ProfileEditCommand
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent

internal class ProfileEditGetCurrentUser(
    private val currentUserRepository: CurrentUserRepository
) : Actor<ProfileEditCommand, ProfileEditEvent> {

    override fun process(commands: Flow<ProfileEditCommand>): Flow<ProfileEditEvent> {
        return commands.filterIsInstance<ProfileEditCommand.GetUserLocalData>()
            .mapLatest { currentUserRepository.getCurrentUser() }
            .filterNotNull()
            .map { ProfileEditEvent.LocalUser(it) }
    }
}
