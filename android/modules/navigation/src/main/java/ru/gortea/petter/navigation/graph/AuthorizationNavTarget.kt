package ru.gortea.petter.navigation.graph

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface AuthorizationNavTarget : Parcelable {
    @Parcelize
    object Authorization : AuthorizationNavTarget

    @Parcelize
    object Registration : AuthorizationNavTarget
}
