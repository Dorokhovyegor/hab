package com.dorokhov.hab.percistance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dorokhov.hab.percistance.entities.*

@Dao
interface CommonDao {

    @Transaction
    @Query("SELECT * From Cycle")
    suspend fun getHabitsInCurrentCycle(): List<CycleWithHabits>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCycle(cycle: Cycle): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaysToHabitWithId(day: List<Day>): List<Long>

    @Update
    suspend fun updateDayWithId(day: Day): Int

    // todo make sure this return only one note
    @Query("""
        SELECT * FROM Cycle
    """)
    suspend fun getCycle(): Cycle?

    // получаем список необходимых привычек, которые надо выполнить в текущий день
    @Query("""
        SELECT * From Day 
        WHERE Day.habitId = (SELECT Habit.habitId FROM Habit
        WHERE Habit.cycleId = (SELECT cycleId FROM Cycle))
        AND Day.date =:date
    """)
    suspend fun getDaysForCurrentCycleInCurrentDate(date: String): List<Day>

    @Query("""
        SELECT * FROM DAY WHERE Day.date =:date
    """)
    suspend fun getAllDays(date: String): List<Day>

    // мне нужно сделать запрос по всем дня и привычкам, отсортировать их
    @Query(
        """
        SELECT * FROM Day 
        WHERE Day.habitId = (SELECT Habit.habitId FROM Habit
        WHERE Habit.cycleId = (SELECT cycleId FROM Cycle))
    """
    )
    suspend fun getAllDaysForCurrentCycle(): List<Day>
}