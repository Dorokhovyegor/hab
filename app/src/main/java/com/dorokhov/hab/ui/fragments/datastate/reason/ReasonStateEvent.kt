package com.dorokhov.hab.ui.fragments.datastate.reason

sealed class ReasonStateEvent {
    class AddReasonEvent(
        val dayId: Int,
        val reasonText: String
    ): ReasonStateEvent()
    class None(): ReasonStateEvent()
}