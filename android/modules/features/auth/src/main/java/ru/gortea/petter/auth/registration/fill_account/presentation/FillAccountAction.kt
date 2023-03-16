package ru.gortea.petter.auth.registration.fill_account.presentation

sealed interface FillAccountAction {
    object ShowImageChooser : FillAccountAction
    object ShowImagePicker : FillAccountAction
}
