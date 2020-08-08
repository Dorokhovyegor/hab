package com.dorokhov.hab.ui.fragments.datastate.viewprogress

import com.dorokhov.hab.percistance.entities.Day

data class ViewProgressViewState(
    var commonProgressFields: CommonProgressFields = CommonProgressFields(),
    var listOfTaskFields: ListOfTaskFields = ListOfTaskFields()
)

data class CommonProgressFields(
    var nameOfTheCycle: String? = null
)

data class ListOfTaskFields(
    var taskList: List<Day> = ArrayList()
)

data class ProgressIndicatorFields(
    var daysIndicator: List<Int> = ArrayList(14)
)