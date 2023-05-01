package ru.gortea.petter.auth.registration.registration_confirm.presentation.handlers

import ru.gortea.petter.arch.store.CancellationHandler
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.data.model.isLoading

internal class RegistrationConfirmCancellationHandler(
    private val repository: RegistrationRepository
) : CancellationHandler<RegistrationConfirmState> {

    override suspend fun onCancel(state: RegistrationConfirmState) {
        if (!state.confirmationStatus.run { isContent || isLoading }) {
            repository.declineRegistration(state.email)
        }
    }
}
