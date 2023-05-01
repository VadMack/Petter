package ru.gortea.petter.pet.data

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.data.util.ContentFileConverter
import ru.gortea.petter.pet.data.api.PetApi
import ru.gortea.petter.pet.data.model.PetUpdateModel

class UpdatePetRepository(
    private val api: PetApi,
    private val contentFileConverter: ContentFileConverter
) : SourceRepository<Unit>(
    source = {
        val model = it as PetUpdateModel
        val petUpdate: Deferred<Unit>
        val avatarUpdate: Deferred<Unit>
        coroutineScope {
            petUpdate = async {
                api.updatePet(model.model.id, model.model)
            }

            avatarUpdate = async {
                if (model.photoPath == null) {
                    model.model.photoPathSegments?.let { (folder, file) ->
                        api.deletePhoto(folder, file)
                    }
                    return@async
                }

                val file = contentFileConverter.fileFromContent(model.photoPath) ?: return@async

                val requestFile = RequestBody.create(
                    MediaType.parse("image/png"),
                    file
                )

                val requestBody = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestFile
                )

                api.uploadAvatar(model.model.id, requestBody)
            }
        }

        listOf(petUpdate, avatarUpdate).awaitAll()
    }
)
