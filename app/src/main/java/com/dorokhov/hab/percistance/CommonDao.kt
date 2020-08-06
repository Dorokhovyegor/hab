package com.dorokhov.hab.percistance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dorokhov.hab.percistance.entities.*

@Dao
interface CommonDao {

    @Transaction
    @Query("SELECT * From Cycle")
    suspend fun getHabitsInCurrentCycle(): List<CycleWithHabits>

    @Query("""
        DELETE From Cycle where Cycle.cycleId = 0
    """)
    suspend fun resetCycle(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCycle(cycle: Cycle): Long

    @Query("UPDATE Cycle SET name =:name WHERE cycleId = 0")
    suspend fun updateCycleName(name: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaysToHabitWithId(day: List<Day>): List<Long>

    @Query("""
            UPDATE Day SET result = :newStaus WHERE dayId =:dayId
        """)
    suspend fun updateDayWithId(dayId: Int, newStaus: Int): Int

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

    @Query(
        """
            UPDATE Day SET noteText = :note where dayId =:dayId
        """
    )
    suspend fun addNoteTextIntoFailureDay(dayId: Int, note: String): Int
}