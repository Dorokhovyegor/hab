package com.dorokhov.hab.percistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dorokhov.hab.percistance.entities.*

@Database(
    entities = [
        Cycle::class,
        Day::class,
        Habit::class
    ], version = 4
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getCommonDao(): CommonDao

    abstract fun getHabitDao(): HabitDao

    companion object {
        const val DATABASE_NAME = "habit_db"
    }
}