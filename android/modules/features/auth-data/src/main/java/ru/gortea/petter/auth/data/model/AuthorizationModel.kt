package ru.gortea.petter.auth.data.model

import kotlinx.serialization.Serializable
import ru.gortea.petter.data.model.Arguments

@Serializable
data class AuthorizationModel(
    val username: String,
    val password: String
): Arguments
