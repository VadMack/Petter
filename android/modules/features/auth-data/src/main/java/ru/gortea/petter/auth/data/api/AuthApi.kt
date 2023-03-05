package ru.gortea.petter.auth.data.api

import retrofit2.http.Body
import retrofit2.http.PUT
import ru.gortea.petter.auth.data.model.RegistrationModel

interface AuthApi {

    @PUT("api/registration")
    suspend fun registration(@Body model: RegistrationModel): String
}
