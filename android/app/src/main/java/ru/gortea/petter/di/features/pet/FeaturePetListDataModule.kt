package ru.gortea.petter.di.features.pet

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.pet.list.data.api.PetListApi
import ru.gortea.petter.pet.list.data.api.PetListApiService
import ru.gortea.petter.profile.data.local.CurrentUserRepository

@Module
class FeaturePetListDataModule {

    @Provides
    fun providePetListApi(retrofit: Retrofit): PetListApi {
        return retrofit.create()
    }

    @Provides
    fun providePetListApiService(api: PetListApi): PetListApiService {
        return PetListApiService(api)
    }

    @Provides
    fun providePetListRepository(
        apiService: PetListApiService,
        currentUserRepository: CurrentUserRepository
    ): PetListRepository {
        return PetListRepository(apiService, currentUserRepository)
    }
}
