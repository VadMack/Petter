package ru.gortea.petter.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import ru.gortea.petter.pet.analytics.PetAnalyticsController

class PetAnalyticsControllerImpl(
    private val analytics: FirebaseAnalytics
) : PetAnalyticsController {

    override fun opened(isMine: Boolean) {
        analytics.logEvent("pet_opened") {
            param("is_mine", isMine.toString())
        }
    }

    override fun editOpened() {
        analytics.logEvent("pet_edit_opened", Bundle.EMPTY)
    }
}
