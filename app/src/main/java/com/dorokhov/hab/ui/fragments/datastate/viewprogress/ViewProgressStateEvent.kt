package com.dorokhov.hab.ui.fragments.datastate.viewprogress

sealed class ViewProgressStateEvent {
    class RequestCommonInformation(val date: String): ViewProgressStateEvent()
    class ChangeDayState(val dayId: Int, val newState: Int): ViewProgressStateEvent()
    class None: ViewProgressStateEvent()
}
