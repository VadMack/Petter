package ru.gortea.petter.auth.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed class AuthorizationNavTarget : NavTarget {
    @Parcelize
    internal object Authorization : AuthorizationNavTarget()

    internal sealed class Registration : AuthorizationNavTarget() {
        @Parcelize
        object RegistrationForm : Registration()

        @Parcelize
        class RegistrationConfirm(
            val email: String,
            val userId: String,
            val username: String,
            val pwd: String
        ) : Registration()
    }
}
