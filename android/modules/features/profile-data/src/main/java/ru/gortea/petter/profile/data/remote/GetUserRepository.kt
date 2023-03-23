package ru.gortea.petter.profile.data.remote

import ru.gortea.petter.data.MapSourceRepository
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.UserModel

class GetUserRepository(
    private val api: ProfileApi,
    userLocalRepository: UserLocalRepository
) : MapSourceRepository<UserModel, UserModel>(
    source = { api.getUser() },
    mapper = { user ->
        userLocalRepository.updateCurrentUser(user)
        user
    }
)
