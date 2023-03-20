package ru.gortea.petter.auth.registration.fill_account.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountCommand
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountEvent
import ru.gortea.petter.profile.data.remote.ProfileUpdateAvatarRepository

internal class FillAccountInitUploadAvatarActor(
    private val repository: ProfileUpdateAvatarRepository
) : Actor<FillAccountCommand, FillAccountEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<FillAccountEvent> {
        return commands.filterIsInstance<FillAccountCommand.InitUploadAvatar>()
            .flatMapLatest { repository.get() }
            .map { FillAccountEvent.UploadAvatarStatus(it) }
    }
}
