package ru.gortea.petter.di.features.profile

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.gortea.petter.data.ContentResolverFileConverter
import ru.gortea.petter.data.util.ContentFileConverter
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.GetUserRepository
import ru.gortea.petter.profile.data.remote.UserUpdateRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi

@Module
class FeatureProfileDataModule {

    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create()
    }

    @Provides
    fun provideContentFIleConverter(context: Context): ContentFileConverter {
        return ContentResolverFileConverter(context)
    }

    @Provides
    fun provideUserUpdateRepository(
        api: ProfileApi,
        currentUserRepository: CurrentUserRepository,
        contentFileConverter: ContentFileConverter
    ): UserUpdateRepository {
        return UserUpdateRepository(api, currentUserRepository, contentFileConverter)
    }

    @Provides
    fun provideGetUserRepository(
        api: ProfileApi,
        currentUserRepository: CurrentUserRepository
    ): GetUserRepository {
        return GetUserRepository(api, currentUserRepository)
    }
}
