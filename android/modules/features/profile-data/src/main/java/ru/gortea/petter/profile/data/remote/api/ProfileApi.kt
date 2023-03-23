package ru.gortea.petter.profile.data.remote.api

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.profile.data.remote.model.UserUpdateModel

interface ProfileApi {

    @PUT("api/users/")
    suspend fun updateUser(@Body model: UserUpdateModel)

    @Multipart
    @POST("api/users/avatar")
    suspend fun uploadAvatar(@Part image: MultipartBody.Part)

    @GET("api/users/{id}")
    suspend fun getUserById(@Path("id") id: String): UserModel
}
