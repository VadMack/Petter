package ru.gortea.petter.di.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.network.PetterRetrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return PetterRetrofit.create()
    }
}
