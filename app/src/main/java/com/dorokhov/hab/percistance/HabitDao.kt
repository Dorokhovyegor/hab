package com.dorokhov.hab.percistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dorokhov.hab.percistance.entities.Habit

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewHabit(habit: Habit): Long

    @Query("SELECT * From habit")
    suspend fun getAllHabits(): List<Habit>

}