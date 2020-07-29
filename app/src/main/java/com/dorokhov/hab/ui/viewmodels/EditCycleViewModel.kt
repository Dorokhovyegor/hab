package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.repositories.cycle.CycleRepository
import com.dorokhov.hab.repositories.habit.HabitRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.editcycle.EditCycleStateEvent
import com.dorokhov.hab.ui.fragments.datastate.editcycle.EditCycleViewState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class EditCycleViewModel
@Inject
constructor(
    private val cycleRepository: CycleRepository,
    private val habitRepository: HabitRepository
) : BaseViewModel<EditCycleStateEvent, EditCycleViewState>() {

    override fun initNewViewState(): EditCycleViewState = EditCycleViewState()

    @InternalCoroutinesApi
    override fun handleStateEvent(it: EditCycleStateEvent): LiveData<DataState<EditCycleViewState>> {
        return when (it) {
            is EditCycleStateEvent.AddNewHabitToCycle -> {
                cycleRepository.addNewHabit(
                    it.title,
                    it.description,
                    it.monday,
                    it.tuesday,
                    it.wednesday,
                    it.thursday,
                    it.friday,
                    it.saturday,
                    it.sunday
                )
            }
            is EditCycleStateEvent.GetCycleInfoWithHabits -> {
                cycleRepository.getCommonInfoWithHabits()
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