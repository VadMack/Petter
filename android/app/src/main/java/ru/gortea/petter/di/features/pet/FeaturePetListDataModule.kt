package ru.gortea.petter.di.features.pet

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.network.createApi
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.pet.list.data.api.PetListApi
import ru.gortea.petter.pet.list.data.api.PetListApiService

@Module
class FeaturePetListDataModule {

    @Provides
    fun providePetListApi(retrofit: Retrofit): PetListApi {
        return retrofit.createApi()
    }

    @Provides
    fun providePetListApiService(api: PetListApi): PetListApiService {
        return PetListApiService(api)
    }

    @Provides
    fun providePetListRepository(apiService: PetListApiService): PetListRepository {
        return PetListRepository(apiService)
    }
}
