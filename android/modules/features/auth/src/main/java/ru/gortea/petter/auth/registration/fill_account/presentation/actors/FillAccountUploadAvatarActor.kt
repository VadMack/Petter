package ru.gortea.petter.auth.registration.fill_account.presentation.actors

import androidx.core.net.toFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountCommand
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountEvent
import ru.gortea.petter.profile.data.remote.ProfileUpdateAvatarRepository
import ru.gortea.petter.profile.data.remote.model.AvatarModel

internal class FillAccountUploadAvatarActor(
    private val repository: ProfileUpdateAvatarRepository
) : Actor<FillAccountCommand, FillAccountEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<FillAccountEvent> {
        return commands.filterIsInstance<FillAccountCommand.UploadAvatar>()
            .mapLatest { repository.invalidate(AvatarModel(it.uri.toFile())) }
            .flatMapMerge { emptyFlow() }
    }
}
