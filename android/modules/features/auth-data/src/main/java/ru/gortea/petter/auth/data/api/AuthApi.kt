package ru.gortea.petter.auth.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.auth.data.model.RegistrationSuccessModel

interface AuthApi {

    @POST("api/registration")
    suspend fun registration(@Body model: RegistrationModel): RegistrationSuccessModel

    @POST("api/registration/confirm")
    suspend fun registrationConfirm(@Body model: RegistrationConfirmModel)
}
