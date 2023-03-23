package ru.gortea.petter.auth.registration.fill_account.presentation

import ru.gortea.petter.profile.data.remote.model.UserUpdateFullModel

internal sealed interface FillAccountCommand {
    class Validate(val state: FillAccountState) : FillAccountCommand
    class UpdateUser(val user: UserUpdateFullModel) : FillAccountCommand

    object InitUpdateUser : FillAccountCommand
}
