package ru.gortea.petter.di

import dagger.Component
import ru.gortea.petter.auth.registration.di.RegistrationComponent
import ru.gortea.petter.di.features.auth.FeatureAuthDataModule
import ru.gortea.petter.di.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FeatureAuthDataModule::class,
        NetworkModule::class
    ]
)
interface PetterAppComponent : RegistrationComponent
