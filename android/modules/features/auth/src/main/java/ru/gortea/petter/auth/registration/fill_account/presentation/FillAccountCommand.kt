package ru.gortea.petter.auth.registration.fill_account.presentation

import android.net.Uri
import ru.gortea.petter.profile.data.model.UserUpdateModel

internal sealed interface FillAccountCommand {
    class Validate(val state: FillAccountState) : FillAccountCommand
    class UploadAvatar(val uri: Uri) : FillAccountCommand
    class UpdateAccount(val user: UserUpdateModel) : FillAccountCommand

    object InitUploadAvatar : FillAccountCommand
    object InitUpdateAccount : FillAccountCommand
}
