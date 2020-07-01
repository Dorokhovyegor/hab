package com.dorokhov.hab.percistance.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity
data class Cycle(
    @PrimaryKey val cycleId: Int,
    val name: String,
    val startDate: String,
    val endDate: String
)