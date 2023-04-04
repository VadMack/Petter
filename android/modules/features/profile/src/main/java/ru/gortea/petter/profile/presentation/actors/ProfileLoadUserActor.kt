package ru.gortea.petter.profile.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.profile.data.remote.GetUserRepository
import ru.gortea.petter.profile.data.remote.model.GetUserModel
import ru.gortea.petter.profile.presentation.ProfileCommand
import ru.gortea.petter.profile.presentation.ProfileEvent

internal class ProfileLoadUserActor(
    private val repository: GetUserRepository
) : Actor<ProfileCommand, ProfileEvent> {

    override fun process(commands: Flow<ProfileCommand>): Flow<ProfileEvent> {
        return commands.filterIsInstance<ProfileCommand.LoadUser>()
            .mapLatest { repository.invalidate(GetUserModel(it.id, it.forceRemote)) }
            .flatMapMerge { emptyFlow() }
    }
}
