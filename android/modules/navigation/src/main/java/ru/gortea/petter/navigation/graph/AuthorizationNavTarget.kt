package ru.gortea.petter.navigation.graph

import kotlinx.parcelize.Parcelize

sealed interface AuthorizationNavTarget : NavTarget {
    @Parcelize
    object Authorization : AuthorizationNavTarget
}
