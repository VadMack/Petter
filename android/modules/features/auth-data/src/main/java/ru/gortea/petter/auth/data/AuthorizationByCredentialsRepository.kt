package ru.gortea.petter.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.AuthorizationModel
import ru.gortea.petter.auth.data.model.AuthorizedUserModel
import ru.gortea.petter.data.SourceRepository

class AuthorizationByCredentialsRepository(
    private val api: AuthApi
) : SourceRepository<AuthorizedUserModel>(
    source = { api.auth(it as AuthorizationModel) },
    coroutineScope = CoroutineScope(Dispatchers.IO)
)
