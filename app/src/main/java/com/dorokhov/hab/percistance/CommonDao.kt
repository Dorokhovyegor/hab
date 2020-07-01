package com.dorokhov.hab.percistance

import androidx.room.*
import com.dorokhov.hab.percistance.entities.Cycle
import com.dorokhov.hab.percistance.entities.CycleWithHabits
import com.dorokhov.hab.percistance.entities.Day

@Dao
interface CommonDao {

    @Transaction
    @Query("SELECT * From Cycle")
    suspend fun getHabitsInCurrentCycle(): List<CycleWithHabits>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCycle(cycle: Cycle): Int

    suspend fun insertDaysToHabitWithId(day: List<Day>): Int

    @Update
    suspend fun updateDayWithId(day: Day): Int

}