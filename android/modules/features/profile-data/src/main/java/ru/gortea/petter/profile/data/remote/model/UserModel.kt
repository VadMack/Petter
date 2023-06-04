package ru.gortea.petter.profile.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.gortea.petter.network.url.BASE_URL

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
        get() = avatarPathShort?.let { avatar -> "http://${BASE_URL}/api/files/${avatar}" }

    val avatarPathSegments: List<String>?
        get() = avatarPathShort?.split("/")
}
