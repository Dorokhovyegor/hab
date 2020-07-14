package com.dorokhov.hab.ui.fragments.datastate.viewprogress

sealed class ViewProgressStateEvent {
    class RequestTasksForThisDay(): ViewProgressStateEvent()
    class None(): ViewProgressStateEvent()
}
