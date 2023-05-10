package ru.gortea.petter.profile.presentation.analytics

import ru.gortea.petter.arch.analytics.AnalyticsHandler
import ru.gortea.petter.profile.analytics.ProfileAnalyticsController
import ru.gortea.petter.profile.presentation.ProfileEvent
import ru.gortea.petter.profile.presentation.ProfileState

internal class ProfileAnalyticsHandler(
    private val controller: ProfileAnalyticsController
) : AnalyticsHandler<ProfileState, ProfileEvent> {

    override fun logEvent(state: ProfileState, event: ProfileEvent) {
        when(event) {
            is ProfileEvent.IsCurrentUser -> controller.profileOpened(event.isCurrentUser)
            else -> Unit
        }
    }
}
