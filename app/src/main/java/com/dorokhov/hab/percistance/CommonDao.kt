package com.dorokhov.hab.percistance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dorokhov.hab.percistance.entities.Cycle
import com.dorokhov.hab.percistance.entities.CycleWithHabits
import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.percistance.entities.HabitWithDays

@Dao
interface CommonDao {

    @Transaction
    @Query("SELECT * From Cycle WHERE cycleId = :id")
    fun getHabitsInCurrentCycle(id: Int): LiveData<CycleWithHabits>

    @Query("SELECT * From Cycle")
    fun getCycles(): LiveData<Cycle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCycle(cycle: Cycle): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaysToHabitWithId(day: List<Day>): List<Long>

    @Update
    suspend fun updateDayWithId(day: Day): Int

    // у цилка есть привычки -> у привычки есть дни -> день долежн быть равен запрашеваемой строчкее
/*    @Transaction
    @Query("""
        SELECT * from Day where date = :dateParam
    """)
    suspend fun getActiveHabitInCurrentDay(dateParam: String, cycleId: Int): List<Day>*/

}