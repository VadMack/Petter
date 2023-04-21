package ru.gortea.petter.auth.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.gortea.petter.auth.controller.model.AuthorizedUserModel
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.data.model.LogoutModel
import ru.gortea.petter.auth.data.model.TokenAuthorizationModel

interface AuthApi {
    @POST("api/auth")
    suspend fun auth(@Body model: CredsAuthorizationModel): AuthorizedUserModel

    @POST("api/refresh-token")
    suspend fun auth(@Body model: TokenAuthorizationModel): AuthorizedUserModel

    @POST("api/logout")
    suspend fun logout(@Body model: LogoutModel)
}
