package com.dorokhov.hab.ui.fragments.datastate.editcycle

import com.dorokhov.hab.percistance.entities.Habit

data class EditCycleViewState(
    var cycleInfoFields: CycleInfoFields = CycleInfoFields(),
    var habitListFields: HabitListFields = HabitListFields()
)

data class CycleInfoFields(
    var nameCycle: String? = null
)

data class HabitListFields(
    var habitList: List<Habit> = ArrayList()
)