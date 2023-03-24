package ru.gortea.petter.profile.ui.state

import android.net.Uri

internal data class ProfileUserModel(
    val avatar: Uri?,
    val name: String,
    val address: String?
)
