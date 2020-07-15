package com.dorokhov.hab.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dorokhov.hab.repositories.cycle.CycleRepository
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.fragments.datastate.viewcycleinfo.ViewCycleStateEvent
import com.dorokhov.hab.ui.fragments.datastate.viewcycleinfo.ViewCycleViewState
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

class ViewCycleViewModel
@Inject
constructor(
    private val repository: CycleRepository
): BaseViewModel<ViewCycleStateEvent, ViewCycleViewState>() {

    override fun initNewViewState(): ViewCycleViewState {
        return ViewCycleViewState()
    }

    @InternalCoroutinesApi
    override fun handleStateEvent(it: ViewCycleStateEvent): LiveData<DataState<ViewCycleViewState>> {
        when (it) {
            is ViewCycleStateEvent.GetFullInfoAboutThisCycleInCurrentDay -> {
                return repository.getCurrentCycleInfo()
            }
            is ViewCycleStateEvent.ChangeStatusOFHabitInCurrentDay -> {
                return liveData {  }
            }
            is ViewCycleStateEvent.SendMessageWithCurrentFailure -> {
                return liveData {  }
            }
            is ViewCycleStateEvent.None -> {
                return liveData {  }
            }
        }
    }
}