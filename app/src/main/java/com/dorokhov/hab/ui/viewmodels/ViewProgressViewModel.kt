package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.repositories.cycle.CycleRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.CommonProgressFields
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressStateEvent
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class ViewProgressViewModel
@Inject
constructor(
    private val cycleRepository: CycleRepository
): BaseViewModel<ViewProgressStateEvent, ViewProgressViewState>() {

    override fun initNewViewState(): ViewProgressViewState {
        return ViewProgressViewState()
    }

    @InternalCoroutinesApi
    override fun handleStateEvent(it: ViewProgressStateEvent): LiveData<DataState<ViewProgressViewState>> {
        when (it) {
            is ViewProgressStateEvent.RequestCommonInformation -> {
                return cycleRepository.getCommonInfoCycle(it.date)
            }
            is ViewProgressStateEvent.None -> {
                return liveData {  }
            }
        }
    }

    fun setCommonCycleInfo(commonProgressFields: CommonProgressFields) {
        val update = getCurrentNewStateOrNew()
        update.commonProgressFields = commonProgressFields
        setViewState(update)
    }

    fun setTaskList(task: List<Day>) {
        val update = getCurrentNewStateOrNew()
        update.listOfTask.taskList = task
        setViewState(update)
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