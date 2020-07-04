package com.dorokhov.hab.ui.fragments.datastate.createhabit

data class CreateNewHabitViewState(
    var habitFields: NewHabitFields = NewHabitFields()
)

data class NewHabitFields(
    var habitTitle: String? = null,
    var habitDescription: String? = null,
    var monday: Boolean = false,
    var tuesday: Boolean = false,
    var wednesday: Boolean = false,
    var thursday: Boolean = false,
    var friday: Boolean = false,
    var saturday: Boolean = false,
    var sunday: Boolean = false
)