package ru.gortea.petter.profile.edit.presentation

sealed interface ProfileEditAction {
    object ShowImageChooser : ProfileEditAction
    object ShowImagePicker : ProfileEditAction
}
