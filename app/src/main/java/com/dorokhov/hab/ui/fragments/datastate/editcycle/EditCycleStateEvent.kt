package com.dorokhov.hab.ui.fragments.datastate.editcycle

sealed class EditCycleStateEvent() {
    class GetCycleInfoWithHabits() : EditCycleStateEvent()
    class DeleteHabitFromCycle(val habitId: Int) : EditCycleStateEvent()
    class AddNewHabitToCycle(
        val title: String,
        val description: String,
        val monday: Boolean,
        val tuesday: Boolean,
        val wednesday: Boolean,
        val thursday: Boolean,
        val friday: Boolean,
        val saturday: Boolean,
        val sunday: Boolean
    ) : EditCycleStateEvent()
    class ResetCycle() : EditCycleStateEvent()
    class None : EditCycleStateEvent()
}
