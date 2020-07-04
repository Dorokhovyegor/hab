package com.dorokhov.hab.ui.fragments.datastate

sealed class ViewHabitStateEvent {

    class GetAllHabits: ViewHabitStateEvent()

    class SearchHabits(): ViewHabitStateEvent()

    class None: ViewHabitStateEvent()

}