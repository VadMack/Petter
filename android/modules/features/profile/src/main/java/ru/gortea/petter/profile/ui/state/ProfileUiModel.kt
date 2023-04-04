package ru.gortea.petter.profile.ui.state

import android.net.Uri
import ru.gortea.petter.profile.presentation.PetsListState

internal data class ProfileUiModel(
    val id: String,
    val avatar: Uri?,
    val name: String,
    val address: String?,
    val canAddPet: Boolean,
    val isFavouritesAvailable: Boolean,
    val petsListState: PetsListState
)
