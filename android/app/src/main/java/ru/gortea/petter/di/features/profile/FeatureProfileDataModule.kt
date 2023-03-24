package ru.gortea.petter.di.features.profile

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gortea.petter.data.ContentResolverFileConverter
import ru.gortea.petter.network.createApi
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.profile.data.remote.GetUserRepository
import ru.gortea.petter.profile.data.remote.UserUpdateRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.util.ContentFileConverter

@Module
class FeatureProfileDataModule {

    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.createApi()
    }

    @Provides
    fun provideContentFIleConverter(context: Context): ContentFileConverter {
        return ContentResolverFileConverter(context)
    }

    @Provides
    fun provideUserUpdateRepository(
        api: ProfileApi,
        userLocalRepository: UserLocalRepository,
        contentFileConverter: ContentFileConverter
    ): UserUpdateRepository {
        return UserUpdateRepository(api, userLocalRepository, contentFileConverter)
    }

    @Provides
    fun provideGetUserRepository(
        api: ProfileApi,
        userLocalRepository: UserLocalRepository
    ): GetUserRepository {
        return GetUserRepository(api, userLocalRepository)
    }
}
