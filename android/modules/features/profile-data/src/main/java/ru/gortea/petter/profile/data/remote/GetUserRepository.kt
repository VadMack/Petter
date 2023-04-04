package ru.gortea.petter.profile.data.remote

import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.GetUserModel
import ru.gortea.petter.profile.data.remote.model.UserModel

class GetUserRepository(
    private val api: ProfileApi,
    private val userLocalRepository: UserLocalRepository
) : SourceRepository<UserModel>(
    source = {
        val model = it as GetUserModel
        val currentUser = userLocalRepository.getCurrentUser()

        val user = when {
            model.forceRemote -> {
                val id = model.id.ifEmpty { currentUser.id }
                api.getUserById(id)
            }
            model.id.isEmpty() || currentUser.id == model.id -> currentUser
            else -> api.getUserById(model.id)
        }

        if (model.forceRemote && model.id == currentUser.id) {
            userLocalRepository.updateCurrentUser(user)
        }

        user
    }
)
