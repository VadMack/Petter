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
        val getUserModel = it as GetUserModel
        val currentUser = userLocalRepository.getCurrentUser()

        if (getUserModel.id.isEmpty() || currentUser.id == getUserModel.id) {
            currentUser
        } else {
            api.getUserById(getUserModel.id)
        }
    }
)
