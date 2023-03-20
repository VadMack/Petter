package ru.gortea.petter.profile.data.remote

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.AvatarModel

class ProfileUpdateAvatarRepository(
    private val api: ProfileApi
) : SourceRepository<Unit>(
    source = {
        val model = it as AvatarModel
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), model.file)
        val part = MultipartBody.Part.create(requestBody)
        api.uploadAvatar(part)
    }
)
