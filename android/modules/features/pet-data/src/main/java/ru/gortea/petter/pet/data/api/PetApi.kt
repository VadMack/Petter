package ru.gortea.petter.pet.data.api

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import ru.gortea.petter.pet.data.model.PetFullModel

interface PetApi {

    @GET("api/ads/{id}")
    fun getPetById(@Path("id") id: String): PetFullModel

    @Multipart
    @POST("api/ads/{id}/images")
    suspend fun uploadAvatar(@Path("id") id: String, @Part image: MultipartBody.Part)

    @PUT("api/ads/{id}")
    fun updatePet(@Path("id") id: String, @Body model: PetFullModel)

    @POST("api/ads")
    fun createPet(@Body model: PetFullModel): PetFullModel

    @DELETE("api/ads/{id}")
    fun deletePet(@Path("id") id: String)

    @DELETE("api/files/users/{folder}/{file}")
    fun deletePhoto(@Path("folder") folder: String, @Path("file") file: String)
}
