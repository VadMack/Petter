package ru.gortea.petter.auth.registration.fill_account.presentation

import android.net.Uri
import ru.gortea.petter.auth.registration.common.FieldState
import ru.gortea.petter.data.model.DataState

internal data class FillAccountState(
    val avatar: Uri? = null,
    val nameFieldState: FieldState = FieldState(),
    val countryFieldState: FieldState = FieldState(),
    val cityFieldState: FieldState = FieldState(),
    val streetFieldState: FieldState = FieldState(),
    val houseFieldState: FieldState = FieldState(),
    val accountUpdateStatus: DataState<Unit> = DataState.Empty,
    val avatarUploadStatus: DataState<Unit> = DataState.Empty
)
