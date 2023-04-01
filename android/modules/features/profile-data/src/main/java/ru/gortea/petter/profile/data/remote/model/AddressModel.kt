package ru.gortea.petter.profile.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressModel(
    val country: String,
    val city: String,
    val street: String,
    val houseNumber: String,
    val metroStation: String
) {

    override fun toString(): String {
        var string = buildString {
            country.appendIfNotEmpty(this)
            city.appendIfNotEmpty(this)
            street.appendIfNotEmpty(this)
            houseNumber.appendIfNotEmpty(this)
            metroStation.appendIfNotEmpty(this)
        }

        if (string.isNotEmpty()) {
            string = string.run { removeRange(lastIndex - 1, length) }
        }

        return string
    }

    private fun String.appendIfNotEmpty(builder: StringBuilder) {
        if (isNotEmpty()) {
            builder.append(this)
            builder.append(", ")
        }
    }
}
