package ru.gortea.petter.profile.edit.presentation

import ru.gortea.petter.profile.data.remote.model.UserUpdateFullModel

internal sealed interface ProfileEditCommand {
    class Validate(val state: ProfileEditState) : ProfileEditCommand
    class UpdateUser(val user: UserUpdateFullModel) : ProfileEditCommand

    object GetUserLocalData : ProfileEditCommand
    object InitUpdateUser : ProfileEditCommand
}
