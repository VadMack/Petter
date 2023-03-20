package ru.gortea.petter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gortea.petter.profile.data.local.UserDao
import ru.gortea.petter.profile.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class PetterDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "PETTER_DATABASE"
    }
}
