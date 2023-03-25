package ru.gortea.petter.profile.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: String,
    val email: String,
    val username: String,
    val displayName: String?,
    val phoneNumber: String?,
    val address: AddressModel?,
    @SerialName("avatarPath") val avatarPathShort: String?
) {
    val avatarPath: String?
        get() = avatarPathShort?.let { avatar -> "http://10.0.2.2:8080/api/files/${avatar}" }

    val avatarPathSegments: List<String>?
        get() = avatarPathShort?.split("/")
}
