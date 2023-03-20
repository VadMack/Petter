package ru.gortea.petter.di.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.gortea.petter.data.PetterDatabase
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): PetterDatabase {
        return Room.databaseBuilder(
            context,
            PetterDatabase::class.java,
            PetterDatabase.DATABASE_NAME
        ).build()
    }

}
