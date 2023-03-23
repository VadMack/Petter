package ru.gortea.petter.profile.edit.presentation

import android.net.Uri
import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.data.model.DataState

internal data class ProfileEditState(
    val avatar: Uri? = null,
    val nameFieldState: FieldState = FieldState(),
    val countryFieldState: FieldState = FieldState(),
    val cityFieldState: FieldState = FieldState(),
    val streetFieldState: FieldState = FieldState(),
    val houseFieldState: FieldState = FieldState(),
    val userUpdateStatus: DataState<Unit> = DataState.Empty,
)
