package ru.gortea.petter.profile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val username: String,
    val displayName: String,
    val phoneNumber: String,
    val country: String,
    val city: String,
    val street: String,
    val houseNumber: String,
    val metroStation: String,
    val avatarPath: String
)
