package ru.gortea.petter.pet.list.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.pet.list.data.model.PetListItemModel

interface PetListApi {

    @GET("api/ads")
    suspend fun getPetList(
        @Query("page") page: Int,
        @Query("size") pageSize: Int,
        @Query("ownerId") ownerId: String?,
        @Query("species") species: Species?,
        @Query("breed") breed: String?,
        @Query("gender") gender: Gender?
    ): List<PetListItemModel>

    @GET("api/users/favourites")
    suspend fun getFavourites(): List<PetListItemModel>
}
