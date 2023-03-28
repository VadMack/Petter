package ru.gortea.petter.pet.ui.state.edit

import android.net.Uri
import ru.gortea.petter.pet.presentation.state.PetField
import ru.gortea.petter.ui_kit.button.ButtonState

internal data class PetEditFullUiModel(
    val photo: Uri?,
    val isShowed: Boolean,
    val canHide: Boolean,
    val saveButtonState: ButtonState,
    val unusedFields: List<PetField>,
    val fields: List<PetField>
)
