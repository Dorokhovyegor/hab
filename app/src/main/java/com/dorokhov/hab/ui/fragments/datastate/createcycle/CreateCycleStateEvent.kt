package com.dorokhov.hab.ui.fragments.datastate.createcycle

sealed class CreateCycleStateEvent {

    data class CreateCycleEvent(
        val name: String,
        val startDate: String
    ): CreateCycleStateEvent()

    class None: CreateCycleStateEvent()

}