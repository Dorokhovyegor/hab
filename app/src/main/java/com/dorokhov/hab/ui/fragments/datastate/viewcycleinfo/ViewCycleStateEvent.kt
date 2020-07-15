package com.dorokhov.hab.ui.fragments.datastate.viewcycleinfo

import com.dorokhov.hab.percistance.entities.Day

sealed class ViewCycleStateEvent {
    class GetFullInfoAboutThisCycleInCurrentDay() : ViewCycleStateEvent()
    class ChangeStatusOFHabitInCurrentDay(val statusHabit: Int, val day: Day) : ViewCycleStateEvent()
    class SendMessageWithCurrentFailure(val message: String, val day: Day) : ViewCycleStateEvent()
    class None() : ViewCycleStateEvent()
}