package com.dorokhov.hab.percistance.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Day(
    @PrimaryKey val dayId: Int,
    val habitId: Int,
    val date: String,
    val result: Int
)

/*
enum class Result {
    OK, 1
    FAILURE, 0
    NEUTRAL_FAILURE 2
}*/
