package ru.gortea.petter.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.di.features.auth.FeatureAuthControllerBinder
import ru.gortea.petter.di.features.auth.FeatureAuthControllerModule
import ru.gortea.petter.di.features.auth.FeatureAuthDataModule
import ru.gortea.petter.di.features.profile.FeatureProfileDataModule
import ru.gortea.petter.di.network.NetworkModule
import ru.gortea.petter.di.storage.StorageModule
import ru.gortea.petter.di.token.TokenModule
import ru.gortea.petter.main.di.MainActivityComponent
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
        FeatureProfileDataModule::class,
        NetworkModule::class,
        TokenModule::class,
        StorageModule::class
    ]
)
interface PetterAppComponent : AuthorizationComponent,
    ProfileComponent,
    ProfileEditComponent,
    SplashComponent,
    MainActivityComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): PetterAppComponent
    }
}
