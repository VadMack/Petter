package ru.gortea.petter.di.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.network.PetterRetrofit
import ru.gortea.petter.token.storage.JwtTokenRepository
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(repository: JwtTokenRepository): Retrofit {
        return PetterRetrofit.create(repository)
    }
}
