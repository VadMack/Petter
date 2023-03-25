package ru.gortea.petter.pet.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.gortea.petter.pet.data.model.PetFullModel

interface PetApi {

    @GET("api/ads/{id}")
    fun getPetById(@Path("id") id: String): PetFullModel

    @PUT("api/ads/{id}")
    fun updatePet(@Path("id") id: String, @Body model: PetFullModel)
}
