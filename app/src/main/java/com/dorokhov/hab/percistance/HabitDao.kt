package com.dorokhov.hab.percistance

import androidx.room.*
import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.percistance.entities.Habit

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewHabit(habit: Habit): Long

    @Query("SELECT * From Habit")
    suspend fun getAllHabits(): List<Habit>

    @Query("DELETE FROM Habit WHERE Habit.habitId =:id")
    suspend fun deleteHabitWithId(id: Int): Int

    @Query("DELETE FROM Day WHERE Day.habitId =:habitId")
    suspend fun deleteDaysFromHabit(habitId: Int): Int

    @Update
    suspend fun updateHabit(habit: Habit): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDaysToCurrentHabit(days: List<Day>): List<Long>
}