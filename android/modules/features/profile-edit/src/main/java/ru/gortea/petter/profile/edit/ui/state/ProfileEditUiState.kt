package ru.gortea.petter.profile.edit.ui.state

import android.net.Uri
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.TextFieldState

internal data class ProfileEditUiState(
    val avatar: Uri?,
    val name: TextFieldState,
    val country: TextFieldState,
    val city: TextFieldState,
    val street: TextFieldState,
    val house: TextFieldState,
    val saveButton: ButtonState
)
