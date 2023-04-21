package ru.gortea.petter.auth.registration.registration_confirm.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmCommand
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmEvent
import ru.gortea.petter.token.storage.TokenRepository

internal class AuthorizeActor(
    private val repository: AuthorizationRepository,
    private val deviceTokenRepository: TokenRepository
) : Actor<RegistrationConfirmCommand, RegistrationConfirmEvent> {

    override fun process(commands: Flow<RegistrationConfirmCommand>): Flow<RegistrationConfirmEvent> {
        return commands.filterIsInstance<RegistrationConfirmCommand.Authorize>()
            .mapLatest {
                val credentials = CredsAuthorizationModel(
                    username = it.username,
                    password = it.pwd,
                    deviceToken = deviceTokenRepository.getToken()
                )
                repository.invalidate(credentials)
            }
            .flatMapMerge { emptyFlow() }
    }
}
