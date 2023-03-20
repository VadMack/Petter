package ru.gortea.petter.profile.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gortea.petter.profile.data.local.entity.UserEntity
import ru.gortea.petter.profile.data.remote.model.AddressModel
import ru.gortea.petter.profile.data.remote.model.UserModel

class UserLocalRepository(
    private val userDao: UserDao
) {
    suspend fun updateCurrentUser(user: UserModel) = withContext(Dispatchers.IO) {
        userDao.updateOrInsert(user.toEntity())
    }

    suspend fun deleteCurrentUser() = withContext(Dispatchers.IO) {
        val user = userDao.get()
        userDao.delete(user)
    }

    suspend fun getCurrentUser(): UserModel = withContext(Dispatchers.IO) {
        userDao.get().toModel()
    }

    private fun UserModel.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            email = email,
            username = username,
            displayName = displayName ?: "",
            phoneNumber = phoneNumber ?: "",
            country = address?.country ?: "",
            city = address?.city ?: "",
            street = address?.street ?: "",
            houseNumber = address?.houseNumber ?: "",
            metroStation = address?.metroStation ?: "",
            avatarPath = avatarPath ?: ""
        )
    }

    private fun UserEntity.toModel(): UserModel {
        return UserModel(
            id = id,
            email = email,
            username = username,
            displayName = displayName,
            phoneNumber = phoneNumber,
            address = AddressModel(
                country = country,
                city = city,
                street = street,
                houseNumber = houseNumber,
                metroStation = metroStation,
            ),
            avatarPath = avatarPath
        )
    }
}