package ru.gortea.petter.profile.data.remote

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.gortea.petter.data.MapSourceRepository
import ru.gortea.petter.data.util.ContentFileConverter
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.profile.data.remote.model.UserUpdateFullModel

class UserUpdateRepository(
    private val api: ProfileApi,
    private val userLocalRepository: UserLocalRepository,
    private val contentFileConverter: ContentFileConverter
) : MapSourceRepository<UserModel, Unit>(
    source = {
        val model = it as UserUpdateFullModel
        val userUpdate: Deferred<Unit>
        val avatarUpdate: Deferred<Unit>
        coroutineScope {
            userUpdate = async {
                api.updateUser(model.userUpdateModel)
            }
            avatarUpdate = async {
                if (model.avatarModel.filePath == null) {
                    userLocalRepository.getCurrentUser().avatarPathSegments?.let { (folder, file) ->
                        api.deletePhoto(folder, file)
                    }
                    return@async
                }

                val file = contentFileConverter.fileFromContent(model.avatarModel.filePath) ?: return@async

                val requestFile = RequestBody.create(
                    MediaType.parse("image/png"),
                    file
                )

                val requestBody = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestFile
                )

                api.uploadAvatar(requestBody)
            }
        }

        listOf(userUpdate, avatarUpdate).awaitAll()
        val id = userLocalRepository.getCurrentUser().id
        api.getUserById(id)
    },
    mapper = { user ->
        userLocalRepository.updateCurrentUser(user)
    }
)
