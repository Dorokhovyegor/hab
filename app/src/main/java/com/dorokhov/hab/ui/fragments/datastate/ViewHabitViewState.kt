package com.dorokhov.hab.ui.fragments.datastate

import com.dorokhov.hab.percistance.entities.Habit

data class ViewHabitViewState(
    var habitFields: HabitFields = HabitFields()
)

data class HabitFields(
    var habits: List<Habit> = ArrayList<Habit>()
)