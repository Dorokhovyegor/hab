package com.dorokhov.hab.di.modules

import android.app.Application
import androidx.room.Room
import com.dorokhov.hab.percistance.AppDataBase
import com.dorokhov.hab.percistance.AppDataBase.Companion.DATABASE_NAME
import com.dorokhov.hab.percistance.CommonDao
import com.dorokhov.hab.percistance.HabitDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDataBase {
        return Room
            .databaseBuilder(app, AppDataBase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCommonDao(dataBase: AppDataBase): CommonDao {
        return dataBase.getCommonDao()
    }

    @Singleton
    @Provides
    fun provideHabitDao(dataBase: AppDataBase): HabitDao {
        return dataBase.getHabitDao()
    }

}