package ru.gortea.petter.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import ru.gortea.petter.profile.edit.analytics.ProfileEditAnalyticsController

class ProfileEditAnalyticsControllerImpl(
    private val analytics: FirebaseAnalytics
) : ProfileEditAnalyticsController {

    override fun profileEditOpened() {
        analytics.logEvent("profile_edit_opened", Bundle.EMPTY)
    }
}
