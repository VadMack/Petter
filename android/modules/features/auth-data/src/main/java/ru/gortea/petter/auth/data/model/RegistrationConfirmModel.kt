package ru.gortea.petter.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationConfirmModel(
    val userId: String,
    val code: Int
)
