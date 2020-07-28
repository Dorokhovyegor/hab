package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.repositories.cycle.CycleRepository
import com.dorokhov.hab.repositories.habit.HabitRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.editcycle.EditCycleStateEvent
import com.dorokhov.hab.ui.fragments.datastate.editcycle.EditCycleViewState
import javax.inject.Inject

class EditCycleViewModel
@Inject
constructor(
    private val cycleRepository: CycleRepository,
    private val habitRepository: HabitRepository
) : BaseViewModel<EditCycleStateEvent, EditCycleViewState>() {

    override fun initNewViewState(): EditCycleViewState = EditCycleViewState()

    override fun handleStateEvent(it: EditCycleStateEvent): LiveData<DataState<EditCycleViewState>> {
        return when (it) {
            is EditCycleStateEvent.AddNewHabitToCycle -> {
                habitRepository.createAndAddNewHabit()
            }
            is EditCycleStateEvent.GetCycleInfoWithHabits -> {
                liveData { }
            }
            is EditCycleStateEvent.DeleteHabitFromCycle -> {
                liveData { }
            }
            is EditCycleStateEvent.ResetCycle -> {
                liveData { }
            }
            is EditCycleStateEvent.None -> {
                liveData { }
            }
        }
    }
}