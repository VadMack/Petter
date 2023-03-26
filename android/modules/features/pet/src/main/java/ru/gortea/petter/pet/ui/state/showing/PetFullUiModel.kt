package ru.gortea.petter.pet.ui.state.showing

import android.net.Uri
import ru.gortea.petter.pet.presentation.state.PetField

internal data class PetFullUiModel(
    val photo: Uri?,
    val canEdit: Boolean,
    val model: PetUiModel,
    val fields: List<PetField>
)
