package com.dorokhov.hab.ui.fragments.datastate.viewprogress

sealed class ViewProgressStateEvent {
    class RequestCommonInformation(cycleId: Int): ViewProgressStateEvent()
    class RequestTasksForThisDay(val date: String): ViewProgressStateEvent()
    class None: ViewProgressStateEvent()
}
