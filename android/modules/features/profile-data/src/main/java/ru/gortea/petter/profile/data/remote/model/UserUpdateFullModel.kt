package ru.gortea.petter.profile.data.remote.model

import ru.gortea.petter.data.model.Arguments

class UserUpdateFullModel(
    val userUpdateModel: UserUpdateModel,
    val avatarModel: AvatarModel
) : Arguments
