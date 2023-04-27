package ru.gortea.petter.di.token

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gortea.petter.data.InMemoryDeviceTokenRepository
import ru.gortea.petter.data.SharedPrefsJwtTokenRepository
import ru.gortea.petter.data.SharedPrefsRefreshTokenRepository
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.DeviceToken
import ru.gortea.petter.token.storage.qualifier.JwtToken
import ru.gortea.petter.token.storage.qualifier.RefreshToken
import javax.inject.Singleton

@Module
class TokenModule {

    @Singleton
    @JwtToken
    @Provides
    fun provideJwtTokenRepository(context: Context): TokenRepository {
        return SharedPrefsJwtTokenRepository(context)
    }

    @Singleton
    @RefreshToken
    @Provides
    fun provideRefreshTokenRepository(context: Context): TokenRepository {
        return SharedPrefsRefreshTokenRepository(context)
    }

    @Singleton
    @DeviceToken
    @Provides
    fun provideDeviceTokenRepository(): TokenRepository {
        return InMemoryDeviceTokenRepository()
    }
}
