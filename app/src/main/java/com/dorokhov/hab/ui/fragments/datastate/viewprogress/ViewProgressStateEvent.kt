package com.dorokhov.hab.ui.fragments.datastate.viewprogress

sealed class ViewProgressStateEvent {
    class RequestCommonInformation(val date: String): ViewProgressStateEvent()
    class None: ViewProgressStateEvent()
}
