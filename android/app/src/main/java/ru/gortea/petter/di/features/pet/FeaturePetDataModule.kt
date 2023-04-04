package ru.gortea.petter.di.features.pet

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.data.util.ContentFileConverter
import ru.gortea.petter.network.createApi
import ru.gortea.petter.pet.data.CreatePetRepository
import ru.gortea.petter.pet.data.DeletePetRepository
import ru.gortea.petter.pet.data.GetPetRepository
import ru.gortea.petter.pet.data.PetLikeRepository
import ru.gortea.petter.pet.data.UpdatePetRepository
import ru.gortea.petter.pet.data.api.PetApi

@Module
class FeaturePetDataModule {

    @Provides
    fun providePetApi(retrofit: Retrofit): PetApi {
        return retrofit.createApi()
    }

    @Provides
    fun provideGetPetRepository(api: PetApi): GetPetRepository {
        return GetPetRepository(api)
    }

    @Provides
    fun provideUpdatePetRepository(
        api: PetApi,
        contentFileConverter: ContentFileConverter
    ): UpdatePetRepository {
        return UpdatePetRepository(api, contentFileConverter)
    }

    @Provides
    fun provideCreatePetRepository(
        api: PetApi,
        contentFileConverter: ContentFileConverter
    ): CreatePetRepository {
        return CreatePetRepository(api, contentFileConverter)
    }

    @Provides
    fun provideDeletePetRepository(api: PetApi): DeletePetRepository {
        return DeletePetRepository(api)
    }

    @Provides
    fun provideLikePetRepository(api: PetApi): PetLikeRepository {
        return PetLikeRepository(api)
    }
}
