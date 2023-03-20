package ru.gortea.petter.navigation.graph

import kotlinx.parcelize.Parcelize

sealed interface RegistrationFlowNavTarget : NavTarget {
    @Parcelize
    object RegistrationForm : RegistrationFlowNavTarget

    @Parcelize
    class RegistrationConfirm(
        val email: String,
        val userId: String,
        val username: String,
        val pwd: String
    ) : RegistrationFlowNavTarget

    @Parcelize
    object FillAccount : RegistrationFlowNavTarget
}
