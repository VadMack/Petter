package ru.gortea.petter.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import ru.gortea.petter.home.analytics.HomeAnalyticsController

class HomeAnalyticsControllerImpl(
    private val analytics: FirebaseAnalytics
) : HomeAnalyticsController {
    override fun homeOpened() {
        analytics.logEvent("home_opened", Bundle.EMPTY)
    }

    override fun filtersOpened() {
        analytics.logEvent("filters_opened", Bundle.EMPTY)
    }
}
