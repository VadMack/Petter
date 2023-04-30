package ru.gortea.petter.profile.data.remote

import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.UserIdModel
import ru.gortea.petter.profile.data.remote.model.UserModel

class GetUserRepository(
    private val api: ProfileApi,
    private val currentUserRepository: CurrentUserRepository
) : SourceRepository<UserModel>(
    source = {
        val model = it as UserIdModel
        val currentUser = currentUserRepository.getCurrentUser()

        val user = when {
            model.forceRemote -> {
                val id = model.id.ifEmpty { currentUser.id }
                api.getUserById(id)
            }
            model.id.isEmpty() || currentUser.id == model.id -> currentUser
            else -> api.getUserById(model.id)
        }

        if (model.forceRemote && model.id == currentUser.id) {
            currentUserRepository.updateCurrentUser(user)
        }

        user
    }
)
