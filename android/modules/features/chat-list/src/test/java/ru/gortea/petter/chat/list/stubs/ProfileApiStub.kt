package ru.gortea.petter.chat.list.stubs

import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.profile.data.remote.model.UserUpdateModel

internal object ProfileApiStub : ProfileApi {
    override suspend fun updateUser(model: UserUpdateModel) = delay(100)

    override suspend fun uploadAvatar(image: MultipartBody.Part) = delay(100)

    override suspend fun getUserById(id: String): UserModel {
        delay(100)
        return UserModel(
            id = id,
            email = "",
            username = "",
            displayName = "",
            phoneNumber = "",
            address = null,
            avatarPathShort = ""
        )
    }

    override suspend fun deletePhoto(folder: String, file: String) = delay(100)
}
