package ru.gortea.petter.profile.data.model

import kotlinx.serialization.Serializable
import ru.gortea.petter.data.model.Arguments

@Serializable
data class UserUpdateModel(
    val displayName: String,
    val phoneNumber: String,
    val address: AddressModel
) : Arguments
