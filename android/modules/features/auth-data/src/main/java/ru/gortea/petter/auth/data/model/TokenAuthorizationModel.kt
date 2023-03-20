package ru.gortea.petter.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenAuthorizationModel(val token: String)
