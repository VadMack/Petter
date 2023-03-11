package ru.gortea.petter.auth.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.gortea.petter.auth.data.model.*
import ru.gortea.petter.profile.data.model.UserModel

interface RegistrationApi {

    @POST("api/registration")
    suspend fun registration(@Body model: RegistrationModel): UserModel

    @POST("api/registration/confirm")
    suspend fun registrationConfirm(@Body model: RegistrationConfirmModel)

    @POST("api/password-reset/resend-confirmation-code")
    suspend fun resendConfirmCode(@Body model: RegistrationEmailModel): UserModel
}
