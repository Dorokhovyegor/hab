package com.dorokhov.hab.ui.fragments.datastate.createhabit

sealed class CreateNewHabitStateEvent {

    data class CreateHabitEvent(
        val title: String,
        val description: String,
        val monday: Boolean,
        val tuesday: Boolean,
        val wednesday: Boolean,
        val thursday: Boolean,
        val friday: Boolean,
        val saturday: Boolean,
        val sunday: Boolean
        ) : CreateNewHabitStateEvent()

    class None : CreateNewHabitStateEvent()

}