package ru.gortea.petter.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class InterlocutorsModel(
    val user1: String,
    val user2: String
)
