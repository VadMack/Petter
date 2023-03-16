package ru.gortea.petter.profile.data

import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.profile.data.api.ProfileApi
import ru.gortea.petter.profile.data.model.UserUpdateModel

class ProfileUpdateRepository(
    private val api: ProfileApi
) : SourceRepository<Unit>(
    source = { api.updateUser(it as UserUpdateModel) }
)
