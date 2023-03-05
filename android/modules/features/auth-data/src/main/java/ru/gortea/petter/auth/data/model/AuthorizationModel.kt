package ru.gortea.petter.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationModel(
    val username: String,
    val password: String
)
