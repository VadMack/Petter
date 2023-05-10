package ru.gortea.petter.app

import android.app.Application
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import ru.gortea.petter.arch.android.ComponentProvider
import ru.gortea.petter.di.DaggerPetterAppComponent
import ru.gortea.petter.di.PetterAppComponent

class PetterApp : Application(), ComponentProvider {
    private lateinit var component: PetterAppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerPetterAppComponent.builder()
            .context(this)
            .analytics(Firebase.analytics)
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getComponent(): T = component as T
}
