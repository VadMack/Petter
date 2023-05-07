package ru.gortea.petter.auth.stubs

import ru.gortea.petter.auth.controller.model.AuthorizedUserModel
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.data.model.LogoutModel
import ru.gortea.petter.auth.data.model.TokenAuthorizationModel
import ru.gortea.petter.network.errors.PetterNetworkError

internal object FailedAuthApiStub : AuthApi {

    override suspend fun auth(model: CredsAuthorizationModel): AuthorizedUserModel {
        throw PetterNetworkError.RequestError(Exception())
    }

    override suspend fun auth(model: TokenAuthorizationModel): AuthorizedUserModel {
        throw PetterNetworkError.AccessDenied()
    }

    override suspend fun logout(model: LogoutModel) = Unit
}
