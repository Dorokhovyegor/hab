package com.dorokhov.hab.percistance.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Day(
    @PrimaryKey val dayId: Int,
    val habitId: Int,
    val date: String,
    val result: Result
)

enum class Result {
    OK,
    FAILURE,
    NEUTRAL_FAILURE
}