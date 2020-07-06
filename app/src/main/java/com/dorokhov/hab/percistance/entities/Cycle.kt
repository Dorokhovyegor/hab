package com.dorokhov.hab.percistance.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity
data class Cycle(
    @PrimaryKey(autoGenerate = false) val cycleId: Int? = 0,
    val name: String,
    val startDate: String,
    val endDate: String
)