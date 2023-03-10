package ru.gortea.petter.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.auth.data.model.RegistrationSuccessModel
import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.data.model.DataState

class RegistrationRepository(
    private val api: AuthApi
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val registrationSource by lazy {
        SourceRepository(
            source = { api.registration(it as RegistrationModel) },
            coroutineScope = coroutineScope
        )
    }

    private val registrationConfirmSource by lazy {
        SourceRepository(
            source = { api.registrationConfirm(it as RegistrationConfirmModel) },
            coroutineScope = coroutineScope
        )
    }

    suspend fun createAccount(args: RegistrationModel): Flow<DataState<RegistrationSuccessModel>> {
        return registrationSource.get(args)
    }

    fun retryCreateAccount(args: RegistrationModel) {
        registrationSource.invalidate(args)
    }

    suspend fun registrationConfirm(args: RegistrationConfirmModel) : Flow<DataState<Unit>> {
        return registrationConfirmSource.get(args)
    }

    fun retryRegistrationConfirm(args: RegistrationConfirmModel) {
        registrationConfirmSource.invalidate(args)
    }
}
