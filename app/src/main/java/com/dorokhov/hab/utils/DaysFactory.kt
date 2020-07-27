package com.dorokhov.hab.utils

import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.percistance.entities.Habit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DaysFactory {

    fun createDays(startDate: String, endDate: String, habit: Habit): List<Day> {
        val pattern = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        val date = simpleDateFormat.parse(startDate)
        var currentDateInMilliseconds = date.time
        val listDays: ArrayList<Day> = ArrayList()
        while (currentDateInMilliseconds.toStringDate() != endDate) {
            // проверка на день недели
            if (habit.monday) {
                if (currentDateInMilliseconds.toStringDate().isCurrentDayOfWeek(1)) {
                    listDays.add(
                        Day(
                            null,
                            habit.habitId!!,
                            habit.name,
                            currentDateInMilliseconds.toStringDate(),
                            -1
                        )
                    )
                }
            }

            if (habit.tuesday) {
                if (currentDateInMilliseconds.toStringDate().isCurrentDayOfWeek(2)) {
                    listDays.add(
                        Day(
                            null,
                            habit.habitId!!,
                            habit.name,
                            currentDateInMilliseconds.toStringDate(),
                            -1
                        )
                    )
                }
            }

            if (habit.wednesday) {
                if (currentDateInMilliseconds.toStringDate().isCurrentDayOfWeek(3)) {
                    listDays.add(
                        Day(
                            null,
                            habit.habitId!!,
                            habit.name,
                            currentDateInMilliseconds.toStringDate(),
                            -1
                        )
                    )
                }
            }

            if (habit.thursday) {
                if (currentDateInMilliseconds.toStringDate().isCurrentDayOfWeek(4)) {
                    listDays.add(
                        Day(
                            null,
                            habit.habitId!!,
                            habit.name,
                            currentDateInMilliseconds.toStringDate(),
                            -1
                        )
                    )
                }
            }

            if (habit.friday) {
                if (currentDateInMilliseconds.toStringDate().isCurrentDayOfWeek(5)) {
                    listDays.add(
                        Day(
                            null,
                            habit.habitId!!,
                            habit.name,
                            currentDateInMilliseconds.toStringDate(),
                            -1
                        )
                    )
                }
            }

            if (habit.saturday) {
                if (currentDateInMilliseconds.toStringDate().isCurrentDayOfWeek(6)) {
                    listDays.add(
                        Day(
                            null,
                            habit.habitId!!,
                            habit.name,
                            currentDateInMilliseconds.toStringDate(),
                            -1
                        )
                    )
                }
            }

            if (habit.sunday) {
                if (currentDateInMilliseconds.toStringDate().isCurrentDayOfWeek(7)) {
                    listDays.add(
                        Day(
                            null,
                            habit.habitId!!,
                            habit.name,
                            currentDateInMilliseconds.toStringDate(),
                            -1
                        )
                    )
                }
            }
            currentDateInMilliseconds = currentDateInMilliseconds + 86400000
        }
        Logger.loge(listDays.toString())
        return listDays
    }

}