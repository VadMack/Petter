package ru.gortea.petter.profile.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.profile.data.remote.GetUserRepository
import ru.gortea.petter.profile.presentation.ProfileCommand
import ru.gortea.petter.profile.presentation.ProfileEvent

internal class ProfileInitLoadUserActor(
    private val repository: GetUserRepository
) : Actor<ProfileCommand, ProfileEvent> {

    override fun process(commands: Flow<ProfileCommand>): Flow<ProfileEvent> {
        return commands.filterIsInstance<ProfileCommand.InitLoadUser>()
            .flatMapLatest { repository.get() }
            .map { ProfileEvent.LoadUserStatus(it) }
    }
}
