package ru.gortea.petter.di.features.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.network.createApi

@Module
class FeatureAuthDataModule {

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.createApi()
    }

    @Provides
    fun provideRegistrationRepository(api: AuthApi): RegistrationRepository {
        return RegistrationRepository(api)
    }
}
