package com.dorokhov.hab.ui.fragments.datastate.viewprogress

import com.dorokhov.hab.percistance.entities.Day

data class ViewProgressViewState(
    var commonProgressFields: CommonProgressFields = CommonProgressFields(),
    var listOfTask: ListOfTask = ListOfTask()
)

data class CommonProgressFields(
    var nameOfTheCycle: String = "",
    var daysToTheEndOfTheCycle: String = "",
    var amountOfHabits: String = ""
)

data class ListOfTask(
    var taskList: List<Day> = ArrayList()
)