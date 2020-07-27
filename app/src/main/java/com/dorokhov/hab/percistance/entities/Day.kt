package com.dorokhov.hab.percistance.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Day(
    @PrimaryKey(autoGenerate = true)
    val dayId: Int?,
    val habitId: Int, // к какой привычке привязано
    val habitName: String,
    val date: String, // текущая дата
    val result: Int // итог как прошел дней
)

/*
enum class Result {
    UNCHECKED = -1
    OK, 1
    FAILURE, 0
    NEUTRAL_FAILURE 2
}*/
