package com.dorokhov.hab.percistance.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CycleWithHabits(
    @Embedded val cycle: Cycle,
    @Relation(
        parentColumn = "cycleId",
        entityColumn = "cycleId"
    )
    val habits: List<Habit>
)