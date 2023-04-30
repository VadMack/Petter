package ru.gortea.petter.di.features.chat_list

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.gortea.petter.chat.list.data.ChatsListRepository
import ru.gortea.petter.chat.list.data.api.ChatListApi
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi

@Module
class FeatureChatListDataModule {

    @Provides
    fun provideChatListApi(retrofit: Retrofit): ChatListApi {
        return retrofit.create()
    }

    @Provides
    fun provideChatLIstRepository(
        api: ChatListApi,
        userApi: ProfileApi,
        currentUserRepository: CurrentUserRepository
    ): ChatsListRepository {
        return ChatsListRepository(
            api = api,
            userApi = userApi,
            currentUserRepository = currentUserRepository
        )
    }
}
