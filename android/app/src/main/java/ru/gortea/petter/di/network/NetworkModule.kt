package ru.gortea.petter.di.network

import android.content.Context
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.data.PetterImageLoaderFactory
import ru.gortea.petter.di.network.qualifier.UnsafeRetrofit
import ru.gortea.petter.network.PetterNetwork
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.JwtToken
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideImageLoader(context: Context, client: OkHttpClient): ImageLoader {
        return PetterImageLoaderFactory.create(context, client)
    }

    @Provides
    @Singleton
    fun provideClient(@JwtToken repository: TokenRepository): OkHttpClient {
        return PetterNetwork.createClient(repository)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, logoutController: LogoutController): Retrofit {
        return PetterNetwork.create(client, logoutController)
    }

    @UnsafeRetrofit
    @Provides
    @Singleton
    fun provideUnsafeRetrofit(client: OkHttpClient): Retrofit {
        return PetterNetwork.create(client)
    }
}
