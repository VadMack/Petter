package ru.gortea.petter.auth.data.model

import kotlinx.serialization.Serializable
import ru.gortea.petter.data.model.Arguments

@Serializable
data class RegistrationConfirmModel(
    val userId: String,
    val code: Int
) : Arguments
