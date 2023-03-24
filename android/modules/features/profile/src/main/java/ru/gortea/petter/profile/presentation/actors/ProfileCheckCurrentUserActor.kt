package ru.gortea.petter.profile.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.profile.presentation.ProfileCommand
import ru.gortea.petter.profile.presentation.ProfileEvent

internal class ProfileCheckCurrentUserActor(
    private val userLocalRepository: UserLocalRepository
) : Actor<ProfileCommand, ProfileEvent> {

    override fun process(commands: Flow<ProfileCommand>): Flow<ProfileEvent> {
        return commands.filterIsInstance<ProfileCommand.IsCurrentUser>()
            .mapLatest { it.id.isEmpty() || userLocalRepository.getCurrentUser().id == it.id }
            .map { ProfileEvent.IsCurrentUser(it) }
    }
}
