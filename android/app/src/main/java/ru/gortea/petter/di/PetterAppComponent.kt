package ru.gortea.petter.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.BindsInstance
import dagger.Component
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.chat.di.ChatComponent
import ru.gortea.petter.chat.list.di.ChatListComponent
import ru.gortea.petter.di.features.auth.FeatureAuthControllerBinder
import ru.gortea.petter.di.features.auth.FeatureAuthControllerModule
import ru.gortea.petter.di.features.auth.FeatureAuthDataModule
import ru.gortea.petter.di.features.chat.FeatureChatDataMessageModel
import ru.gortea.petter.di.features.chat.FeatureChatDataModule
import ru.gortea.petter.di.features.chat.FeatureChatModule
import ru.gortea.petter.di.features.chat_list.FeatureChatListDataModule
import ru.gortea.petter.di.features.chat_list.FeatureChatListModule
import ru.gortea.petter.di.features.home.FeatureHomeModule
import ru.gortea.petter.di.features.pet.FeaturePetDataModule
import ru.gortea.petter.di.features.pet.FeaturePetListDataModule
import ru.gortea.petter.di.features.pet.FeaturePetModule
import ru.gortea.petter.di.features.profile.FeatureProfileDataModule
import ru.gortea.petter.di.features.profile.FeatureProfileModule
import ru.gortea.petter.di.features.profile.edit.FeatureProfileEditModule
import ru.gortea.petter.di.formatters.FormattersModule
import ru.gortea.petter.di.network.NetworkModule
import ru.gortea.petter.di.storage.StorageModule
import ru.gortea.petter.di.token.TokenModule
import ru.gortea.petter.home.di.HomeComponent
import ru.gortea.petter.main.di.MainActivityComponent
import ru.gortea.petter.notifications.di.NotificationsServiceComponent
import ru.gortea.petter.pet.di.PetComponent
import ru.gortea.petter.pet.list.di.PetListComponent
import ru.gortea.petter.profile.di.ProfileComponent
import ru.gortea.petter.profile.edit.di.ProfileEditComponent
import ru.gortea.petter.splash.di.SplashComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FeatureAuthDataModule::class,
        FeatureAuthControllerModule::class,
        FeatureAuthControllerBinder::class,

        FeatureHomeModule::class,

        FeatureChatModule::class,
        FeatureChatDataModule::class,
        FeatureChatDataMessageModel::class,

        FeatureChatListModule::class,
        FeatureChatListDataModule::class,

        FeaturePetModule::class,
        FeaturePetDataModule::class,
        FeaturePetListDataModule::class,

        FeatureProfileModule::class,
        FeatureProfileDataModule::class,
        FeatureProfileEditModule::class,

        FormattersModule::class,
        NetworkModule::class,
        TokenModule::class,
        StorageModule::class
    ]
)
interface PetterAppComponent : AuthorizationComponent,
    HomeComponent,
    ChatListComponent,
    ChatComponent,
    PetComponent,
    PetListComponent,
    ProfileComponent,
    ProfileEditComponent,
    SplashComponent,
    NotificationsServiceComponent,
    MainActivityComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun analytics(analytics: FirebaseAnalytics): Builder
        fun build(): PetterAppComponent
    }
}
