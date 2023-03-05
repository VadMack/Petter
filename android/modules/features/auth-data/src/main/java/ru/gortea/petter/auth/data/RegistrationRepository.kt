package ru.gortea.petter.auth.data

import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.data.model.DataState

class RegistrationRepository(
    private val api: AuthApi
) {
    private val registrationSourceLazy = lazy {
        SourceRepository(
            source = { api.registration(it as RegistrationModel) }
        )
    }

    private val registrationSource by registrationSourceLazy

    private lateinit var registrationSourceFlow: Flow<DataState<String>>

    suspend fun getRegistrationSource(args: RegistrationModel): Flow<DataState<String>> {
        if (registrationSourceLazy.isInitialized()) {
            registrationSource.invalidate(args)
        } else {
            registrationSourceFlow = registrationSource.get(args)
        }

        return registrationSourceFlow
    }
}
