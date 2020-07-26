package com.dorokhov.hab.percistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.percistance.entities.Habit

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewHabit(habit: Habit): Long

    @Query("SELECT * From Habit")
    suspend fun getAllHabits(): List<Habit>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDaysToCurrentHabit(days: List<Day>): List<Long>
}