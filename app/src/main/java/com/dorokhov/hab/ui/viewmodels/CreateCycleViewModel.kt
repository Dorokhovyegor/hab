package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.repositories.cycle.CycleRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.createcycle.CreateCycleStateEvent
import com.dorokhov.hab.ui.fragments.datastate.createcycle.CreateCycleViewState
import com.dorokhov.hab.ui.fragments.datastate.createhabit.CreateNewHabitStateEvent
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class CreateCycleViewModel
@Inject
constructor(
    private val repository: CycleRepository
) : BaseViewModel<CreateCycleStateEvent, CreateCycleViewState>() {

    override fun initNewViewState(): CreateCycleViewState = CreateCycleViewState()

    @InternalCoroutinesApi
    override fun handleStateEvent(it: CreateCycleStateEvent): LiveData<DataState<CreateCycleViewState>> {
        when (it) {
            is CreateCycleStateEvent.CreateCycleEvent -> {
                return repository.createNewCycle(it.name, it.startDate)
            }
            is CreateCycleStateEvent.None -> {
                return liveData {}
            }
        }
    }

    private fun handlePendingData() {
        setStateEvent(CreateCycleStateEvent.None())
    }

    fun cancelActiveJobs() {
        repository.cancelActiveJobs()
        handlePendingData()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}