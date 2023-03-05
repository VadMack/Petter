package ru.gortea.petter.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.RegistrationModel
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

    fun retryCreateAccount(args: RegistrationModel) {
        registrationSource.invalidate(args)
    }
    suspend fun createAccount(args: RegistrationModel): Flow<DataState<String>> {
        return registrationSource.get(args)
    }
}
