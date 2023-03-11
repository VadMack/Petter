package ru.gortea.petter.di.features.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.auth.data.AuthorizationByCredentialsRepository
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.api.RegistrationApi
import ru.gortea.petter.network.createApi

@Module
class FeatureAuthDataModule {

    @Provides
    fun provideRegistrationApi(retrofit: Retrofit): RegistrationApi {
        return retrofit.createApi()
    }

    @Provides
    fun provideAuthorizationApi(retrofit: Retrofit): AuthApi {
        return retrofit.createApi()
    }

    @Provides
    fun provideRegistrationRepository(api: RegistrationApi): RegistrationRepository {
        return RegistrationRepository(api)
    }

    @Provides
    fun provideAuthByCredentialsRepository(api: AuthApi): AuthorizationByCredentialsRepository {
        return AuthorizationByCredentialsRepository(api)
    }
}
