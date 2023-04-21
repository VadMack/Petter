package ru.gortea.petter.di.features.auth

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.gortea.petter.auth.controller.LoginController
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.auth.data.LogoutRepository
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.data.api.AuthApi
import ru.gortea.petter.auth.data.api.RegistrationApi
import ru.gortea.petter.data.PetterDatabase
import ru.gortea.petter.di.network.qualifier.UnsafeRetrofit
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.local.UserDao
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.DeviceToken
import ru.gortea.petter.token.storage.qualifier.RefreshToken

@Module
class FeatureAuthDataModule {

    @Provides
    fun provideRegistrationApi(retrofit: Retrofit): RegistrationApi {
        return retrofit.create()
    }

    @Provides
    fun provideAuthorizationApi(retrofit: Retrofit): AuthApi {
        return retrofit.create()
    }

    @Provides
    fun provideRegistrationRepository(api: RegistrationApi): RegistrationRepository {
        return RegistrationRepository(api)
    }

    @Provides
    fun provideAuthByCredentialsRepository(
        api: AuthApi,
        @RefreshToken refreshRepository: TokenRepository,
        @DeviceToken deviceTokenRepository: TokenRepository,
        loginController: LoginController,
        logoutController: LogoutController
    ): AuthorizationRepository {
        return AuthorizationRepository(
            api = api,
            refreshTokenRepository = refreshRepository,
            deviceTokenRepository = deviceTokenRepository,
            loginController = loginController,
            logoutController = logoutController
        )
    }

    @Provides
    fun provideUserDao(database: PetterDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideUserLocalRepository(userDao: UserDao): CurrentUserRepository {
        return CurrentUserRepository(userDao)
    }

    @Provides
    fun provideLogoutRepository(
        @UnsafeRetrofit retrofit: Retrofit,
        @DeviceToken deviceTokenRepository: TokenRepository
    ): LogoutRepository {
        return LogoutRepository(retrofit.create(), deviceTokenRepository)
    }
}
