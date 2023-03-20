package ru.gortea.petter.di.features.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.api.RegistrationApi
import ru.gortea.petter.data.PetterDatabase
import ru.gortea.petter.di.token.qualifier.RefreshToken
import ru.gortea.petter.network.createApi
import ru.gortea.petter.profile.data.local.UserDao
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.token.storage.TokenRepository

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
    fun provideAuthByCredentialsRepository(
        api: AuthApi,
        @RefreshToken repository: TokenRepository
    ): AuthorizationRepository {
        return AuthorizationRepository(api, repository)
    }

    @Provides
    fun provideUserDao(database: PetterDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideUserLocalRepository(userDao: UserDao): UserLocalRepository {
        return UserLocalRepository(userDao)
    }
}
