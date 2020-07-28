package com.dorokhov.hab.ui.fragments.datastate.editcycle

import com.dorokhov.hab.percistance.entities.Habit

data class EditCycleViewState(
    var cycleInfoFields: CycleInfoFields = CycleInfoFields()
)

data class CycleInfoFields(
    var nameCycle: String? = null
)

data class HabitListFields(
    var habitList: ArrayList<Habit> = ArrayList()
)