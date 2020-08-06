package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.repositories.day.TaskRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.reason.ReasonStateEvent
import com.dorokhov.hab.ui.fragments.datastate.reason.ReasonViewState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class ReasonViewModel
@Inject
constructor(
    private var taskRepository: TaskRepository
) : BaseViewModel<ReasonStateEvent, ReasonViewState>() {

    override fun initNewViewState(): ReasonViewState {
        return ReasonViewState(
            textReason = "Здесь введите причину провала"
        )
    }

    @InternalCoroutinesApi
    override fun handleStateEvent(it: ReasonStateEvent): LiveData<DataState<ReasonViewState>> {
        return when (it) {
            is ReasonStateEvent.AddReasonEvent -> {
                taskRepository.addReason(it.dayId, it.reasonText)
            }
            is ReasonStateEvent.None -> {
                liveData { }
            }
        }
    }
}