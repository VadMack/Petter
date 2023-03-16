package ru.gortea.petter.di.features.profile

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.network.createApi
import ru.gortea.petter.profile.data.ProfileUpdateAvatarRepository
import ru.gortea.petter.profile.data.ProfileUpdateRepository
import ru.gortea.petter.profile.data.api.ProfileApi

@Module
class FeatureProfileDataModule {

    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.createApi()
    }

    @Provides
    fun provideProfileUpdateRepository(api: ProfileApi): ProfileUpdateRepository {
        return ProfileUpdateRepository(api)
    }

    @Provides
    fun provideProfileUpdateAvatarRepository(api: ProfileApi): ProfileUpdateAvatarRepository {
        return ProfileUpdateAvatarRepository(api)
    }
}
