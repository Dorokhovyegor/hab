package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.repositories.habit.HabitRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.Loading
import com.dorokhov.hab.ui.fragments.datastate.CreateNewHabitStateEvent
import com.dorokhov.hab.ui.fragments.datastate.CreateNewHabitViewState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class CreateHabitViewModel
@Inject
constructor(
    private val repository: HabitRepository
) : BaseViewModel<CreateNewHabitStateEvent, CreateNewHabitViewState>() {

    override fun initNewViewState(): CreateNewHabitViewState {
        return CreateNewHabitViewState()
    }

    @InternalCoroutinesApi
    override fun handleStateEvent(it: CreateNewHabitStateEvent): LiveData<DataState<CreateNewHabitViewState>> {
        when (it) {
            is CreateNewHabitStateEvent.CreateHabitEvent -> {
                return repository.createNewHabit(
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
            is CreateNewHabitStateEvent.None -> {
                return liveData {
                    emit(
                        DataState(
                            null,
                            Loading(false),
                            null
                        )
                    )
                }
            }
        }
    }

    private fun handlePendingData() {
        setStateEvent(CreateNewHabitStateEvent.None())
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