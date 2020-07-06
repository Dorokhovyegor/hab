package com.dorokhov.hab.ui.fragments.datastate.createcycle

data class CreateCycleViewState(
    var newCycleFields: NewCycleFields = NewCycleFields()
)

data class NewCycleFields(
    var name: String? = null,
    var startDate: String? = null,
    var endDate: String? = null
)