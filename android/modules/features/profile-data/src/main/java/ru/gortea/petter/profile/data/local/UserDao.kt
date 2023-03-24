package ru.gortea.petter.profile.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ru.gortea.petter.profile.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM userEntity LIMIT 1")
    fun get(): UserEntity

    @Upsert
    fun updateOrInsert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}
