package ru.gortea.petter.auth.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.gortea.petter.auth.data.model.AuthorizationModel
import ru.gortea.petter.auth.data.model.AuthorizedUserModel

interface AuthApi {
    @POST("api/auth")
    suspend fun auth(@Body model: AuthorizationModel): AuthorizedUserModel
}
