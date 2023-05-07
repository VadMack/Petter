package ru.gortea.petter.auth.stubs

import kotlinx.coroutines.delay
import ru.gortea.petter.auth.controller.model.AuthorizedUserModel
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.data.model.LogoutModel
import ru.gortea.petter.auth.data.model.TokenAuthorizationModel
import ru.gortea.petter.profile.data.remote.model.UserModel

internal object AuthApiStub : AuthApi {

    override suspend fun auth(model: CredsAuthorizationModel): AuthorizedUserModel {
        delay(100)
        return AuthorizedUserModel(
            user = UserModel(
                id = "creds_id",
                email = "",
                username = "",
                displayName = "",
                phoneNumber = "",
                address = null,
                avatarPathShort = null
            ),
            refreshToken = "refreshToken"
        )
    }

    override suspend fun auth(model: TokenAuthorizationModel): AuthorizedUserModel {
        delay(100)
        return AuthorizedUserModel(
            user = UserModel(
                id = "refresh_token_id",
                email = "",
                username = "",
                displayName = "",
                phoneNumber = "",
                address = null,
                avatarPathShort = null
            ),
            refreshToken = "refreshToken"
        )
    }

    override suspend fun logout(model: LogoutModel) = delay(100)
}
