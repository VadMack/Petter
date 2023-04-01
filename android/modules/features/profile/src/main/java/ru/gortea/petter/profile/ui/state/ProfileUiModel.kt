package ru.gortea.petter.profile.ui.state

import android.net.Uri

internal data class ProfileUiModel(
    val id: String,
    val avatar: Uri?,
    val name: String,
    val address: String?,
    val canAddPet: Boolean
)
