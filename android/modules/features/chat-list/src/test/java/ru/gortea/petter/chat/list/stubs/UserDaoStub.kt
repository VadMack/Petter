package ru.gortea.petter.chat.list.stubs

import kotlinx.coroutines.delay
import ru.gortea.petter.profile.data.local.UserDao
import ru.gortea.petter.profile.data.local.entity.UserEntity

internal class UserDaoStub(private val myId: String = "myId") : UserDao {
    override suspend fun get(): UserEntity {
        delay(500)
        return UserEntity(
            id = myId,
            email = "",
            username = "",
            displayName = "",
            phoneNumber = "",
            country = "",
            city = "",
            street = "",
            houseNumber = "",
            metroStation = "",
            avatarPath = ""
        )
    }

    override suspend fun updateOrInsert(user: UserEntity) = Unit

    override suspend fun delete(user: UserEntity) = Unit
}
