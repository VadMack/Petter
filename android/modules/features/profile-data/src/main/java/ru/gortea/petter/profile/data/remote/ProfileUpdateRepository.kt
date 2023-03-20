package ru.gortea.petter.profile.data.remote

import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.UserUpdateModel

class ProfileUpdateRepository(
    private val api: ProfileApi
) : SourceRepository<Unit>(
    source = { api.updateUser(it as UserUpdateModel) }
)
