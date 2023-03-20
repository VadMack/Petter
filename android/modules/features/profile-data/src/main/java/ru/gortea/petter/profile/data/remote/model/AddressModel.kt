package ru.gortea.petter.profile.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressModel(
    val country: String,
    val city: String,
    val street: String,
    val houseNumber: String,
    val metroStation: String
)
