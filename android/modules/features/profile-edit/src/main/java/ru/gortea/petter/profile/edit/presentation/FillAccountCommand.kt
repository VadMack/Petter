package ru.gortea.petter.profile.edit.presentation

import ru.gortea.petter.profile.data.remote.model.UserUpdateFullModel

internal sealed interface FillAccountCommand {
    class Validate(val state: ProfileEditState) : FillAccountCommand
    class UpdateUser(val user: UserUpdateFullModel) : FillAccountCommand

    object GetUserLocalData : FillAccountCommand
    object InitUpdateUser : FillAccountCommand
}
