package com.dorokhov.hab.ui.fragments.datastate.editcycle

sealed class EditCycleStateEvent() {
    class GetCycleInfoWithHabits() : EditCycleStateEvent()
    class DeleteHabitFromCycle(val habitId: Int) : EditCycleStateEvent()
    class AddNewHabitToCycle(): EditCycleStateEvent()
    class ResetCycle() : EditCycleStateEvent()
    class None: EditCycleStateEvent()
}
