package com.dorokhov.hab.ui.fragments.datastate.viewcycleinfo

import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.percistance.entities.Habit

data class ViewCycleViewState(
    var commonInfoCycleFields: CommonInfoCycleFields = CommonInfoCycleFields(),
    var habitsInCurrentDayFields: HabitsInCurrentDayFields = HabitsInCurrentDayFields()
)

data class CommonInfoCycleFields(
    var nameCycle: String? = null,
    var amountOfHabits: String? = null,
    var daysToTheEnd: String? = null
)

data class HabitsInCurrentDayFields(
    var habitList: List<Day> = ArrayList<Day>()
)