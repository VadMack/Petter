package ru.gortea.petter.di.network

import dagger.Module
import dagger.Provides
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import ru.gortea.petter.network.PetterRetrofit
import java.util.*
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()

        return OkHttpClient.Builder()
            .connectionSpecs(Collections.singletonList(spec))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return PetterRetrofit.create(client)
    }
}
