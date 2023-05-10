package ru.gortea.petter.di.features.profile.edit

import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import ru.gortea.petter.analytics.ProfileEditAnalyticsControllerImpl
import ru.gortea.petter.profile.edit.analytics.ProfileEditAnalyticsController

@Module
class FeatureProfileEditModule {

    @Provides
    fun provideProfileEditAnalyticsController(
        analytics: FirebaseAnalytics
    ): ProfileEditAnalyticsController {
        return ProfileEditAnalyticsControllerImpl(analytics)
    }
}
