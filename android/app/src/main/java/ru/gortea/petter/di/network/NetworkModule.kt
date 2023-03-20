package ru.gortea.petter.di.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.di.token.qualifier.JwtToken
import ru.gortea.petter.network.PetterRetrofit
import ru.gortea.petter.token.storage.TokenRepository
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(@JwtToken repository: TokenRepository): Retrofit {
        return PetterRetrofit.create(repository)
    }
}
