package ru.gortea.petter.profile.data.api

import retrofit2.http.Body
import retrofit2.http.PUT
import ru.gortea.petter.profile.data.model.UserUpdateModel

interface ProfileApi {

    @PUT("api/users")
    suspend fun updateUser(@Body model: UserUpdateModel)
}
