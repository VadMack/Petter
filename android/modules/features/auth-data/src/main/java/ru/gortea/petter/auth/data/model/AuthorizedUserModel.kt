package ru.gortea.petter.auth.data.model

import kotlinx.serialization.Serializable
import ru.gortea.petter.profile.data.remote.model.UserModel

@Serializable
data class AuthorizedUserModel(
    val user: UserModel,
    val refreshToken: String
)
