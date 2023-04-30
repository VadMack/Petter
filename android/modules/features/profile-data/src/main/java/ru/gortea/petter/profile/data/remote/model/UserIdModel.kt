package ru.gortea.petter.profile.data.remote.model

import ru.gortea.petter.data.model.Arguments

data class UserIdModel(
    val id: String,
    val forceRemote: Boolean = false
) : Arguments
