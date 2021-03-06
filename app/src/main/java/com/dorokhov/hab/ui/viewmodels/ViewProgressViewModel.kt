package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.repositories.cycle.CycleRepository
import com.dorokhov.hab.repositories.day.TaskRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.CommonProgressFields
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressStateEvent
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class ViewProgressViewModel
@Inject
constructor(
    private val cycleRepository: CycleRepository,
    private val taskRepository: TaskRepository
) : BaseViewModel<ViewProgressStateEvent, ViewProgressViewState>() {

    override fun initNewViewState(): ViewProgressViewState {
        return ViewProgressViewState()
    }

    @InternalCoroutinesApi
    override fun handleStateEvent(it: ViewProgressStateEvent): LiveData<DataState<ViewProgressViewState>> {
        return when (it) {
            is ViewProgressStateEvent.RequestCommonInformation -> {
                cycleRepository.getCommonInfoWithTasks(it.date)
            }
            is ViewProgressStateEvent.ChangeDayState -> {
                taskRepository.updateTaskStatus(it.dayId, it.newState)
            }
            is ViewProgressStateEvent.None -> {
                liveData { }
            }
        }
    }

    fun cancelActiveJobs() {
        cycleRepository.cancelActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(ViewProgressStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}