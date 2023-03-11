package ru.gortea.petter.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.auth.data.api.RegistrationApi
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationEmailModel
import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.model.UserModel

class RegistrationRepository(
    private val api: RegistrationApi
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

    private val resendConfirmCodeSource by lazy {
        SourceRepository(
            source = { api.resendConfirmCode(it as RegistrationEmailModel) },
            coroutineScope = coroutineScope
        )
    }

    suspend fun createAccount(args: RegistrationModel): Flow<DataState<UserModel>> {
        return registrationSource.get(args)
    }

    fun retryCreateAccount(args: RegistrationModel) {
        registrationSource.invalidate(args)
    }

    suspend fun registrationConfirm(args: RegistrationConfirmModel): Flow<DataState<Unit>> {
        return registrationConfirmSource.get(args)
    }

    fun retryRegistrationConfirm(args: RegistrationConfirmModel) {
        registrationConfirmSource.invalidate(args)
    }

    suspend fun resendConfirmCode(args: RegistrationEmailModel): Flow<DataState<UserModel>> {
        return resendConfirmCodeSource.get(args)
    }

    fun retryResendConfirmCode(args: RegistrationEmailModel) {
        resendConfirmCodeSource.invalidate(args)
    }
}
