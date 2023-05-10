package ru.gortea.petter.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import ru.gortea.petter.profile.analytics.ProfileAnalyticsController

class ProfileAnalyticsControllerImpl(
    private val analytics: FirebaseAnalytics
) : ProfileAnalyticsController {

    override fun profileOpened(isForeign: Boolean) {
        analytics.logEvent("profile_opened") {
            param("isForeign", isForeign.toString())
        }
    }
}
