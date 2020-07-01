package com.dorokhov.hab.percistance.entities

import androidx.room.Embedded
import androidx.room.Relation

data class HabitWithDays(
    @Embedded val habit: Habit,
    @Relation(
        parentColumn = "habitId",
        entityColumn = "habitId"
    )
    val days: List<Day>
)