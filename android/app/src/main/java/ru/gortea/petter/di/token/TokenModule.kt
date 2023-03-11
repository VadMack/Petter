package ru.gortea.petter.di.token

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gortea.petter.data.SharedPrefsTokenRepository
import ru.gortea.petter.token.storage.JwtTokenRepository
import javax.inject.Singleton

@Module
class TokenModule {

    @Singleton
    @Provides
    fun provideTokenRepository(context: Context): JwtTokenRepository {
        return SharedPrefsTokenRepository(context)
    }
}
