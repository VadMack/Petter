package ru.gortea.petter.profile.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateModel(
    val displayName: String,
    val phoneNumber: String,
    val address: AddressModel
)
