package ru.gortea.petter.auth.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.gortea.petter.auth.data.model.RegistrationModel

interface AuthApi {

    @POST("api/registration")
    suspend fun registration(@Body model: RegistrationModel): String
}
