package ru.gortea.petter.home.di

import ru.gortea.petter.home.analytics.HomeAnalyticsController
import ru.gortea.petter.home.navigation.HomeExternalNodeProvider

interface HomeComponent {
    val homeNodeProvider: HomeExternalNodeProvider

    val homeAnalyticsController: HomeAnalyticsController
}
