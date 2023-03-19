package ru.gortea.petter.profile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: String,
    val email: String,
    val username: String,
    val displayName: String?,
    val phoneNumber: String?,
    val address: AddressModel?,
    val avatarPath: String?
)
