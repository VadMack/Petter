package ru.gortea.petter.auth.data.model

import kotlinx.serialization.Serializable
import ru.gortea.petter.data.model.Arguments

@Serializable
data class RegistrationModel(
    val username: String,
    val password: String,
    val email: String
) : Arguments
