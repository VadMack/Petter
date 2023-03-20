package ru.gortea.petter.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.AuthorizedUserModel
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.data.model.TokenAuthorizationModel
import ru.gortea.petter.data.MapSourceRepository
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.token.storage.TokenRepository

class AuthorizationRepository(
    private val api: AuthApi,
    private val tokenRepository: TokenRepository
) : MapSourceRepository<AuthorizedUserModel, UserModel>(
    source = {
        if (it is CredsAuthorizationModel) {
            api.auth(it)
        } else {
            api.auth(TokenAuthorizationModel(tokenRepository.getToken()))
        }
    },
    mapper = {
        tokenRepository.updateToken(it.refreshToken)
        it.user
    },
    coroutineScope = CoroutineScope(Dispatchers.IO)
)
