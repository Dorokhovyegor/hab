package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.percistance.entities.Habit
import com.dorokhov.hab.repositories.habit.HabitRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.ViewHabitStateEvent
import com.dorokhov.hab.ui.fragments.datastate.ViewHabitViewState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class HabitListViewModel
@Inject
constructor(
    private val repository: HabitRepository
) : BaseViewModel<ViewHabitStateEvent, ViewHabitViewState>() {

    override fun initNewViewState(): ViewHabitViewState = ViewHabitViewState()

    @InternalCoroutinesApi
    override fun handleStateEvent(it: ViewHabitStateEvent): LiveData<DataState<ViewHabitViewState>> {
        when (it) {
            is ViewHabitStateEvent.GetAllHabits -> {
                return repository.getAllHabits()
            }
            is ViewHabitStateEvent.SearchHabits -> {
                return liveData {}
            }
            is ViewHabitStateEvent.None -> {
                return liveData {}
            }
        }
    }

    fun setHabitListData(habits: List<Habit>) {
        val update = getCurrentNewStateOrNew()
        update.habitFields.habits = habits
        setViewState(update)
    }

    fun cancelActiveJobs() {
        repository.cancelActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(ViewHabitStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}