package ru.gortea.petter.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gortea.petter.auth.registration.di.RegistrationComponent
import ru.gortea.petter.di.features.auth.FeatureAuthDataModule
import ru.gortea.petter.di.features.profile.FeatureProfileDataModule
import ru.gortea.petter.di.network.NetworkModule
import ru.gortea.petter.di.token.TokenModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FeatureAuthDataModule::class,
        FeatureProfileDataModule::class,
        NetworkModule::class,
        TokenModule::class
    ]
)
interface PetterAppComponent : RegistrationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): PetterAppComponent
    }
}
