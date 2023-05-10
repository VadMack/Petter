package ru.gortea.petter.profile.edit.presentation.analytics

import ru.gortea.petter.arch.analytics.AnalyticsHandler
import ru.gortea.petter.profile.edit.analytics.ProfileEditAnalyticsController
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent
import ru.gortea.petter.profile.edit.presentation.ProfileEditState

internal class ProfileEditAnalyticsHandler(
    private val controller: ProfileEditAnalyticsController
) : AnalyticsHandler<ProfileEditState, ProfileEditEvent>{

    override fun logEvent(state: ProfileEditState, event: ProfileEditEvent) {
        when(event) {
            is ProfileEditEvent.InitApi -> controller.profileEditOpened()
            else -> Unit
        }
    }
}
