package com.dorokhov.hab.di.modules

import androidx.lifecycle.ViewModel
import com.dorokhov.hab.di.ViewModelKey
import com.dorokhov.hab.ui.viewmodels.CreateCycleViewModel
import com.dorokhov.hab.ui.viewmodels.CreateHabitViewModel
import com.dorokhov.hab.ui.viewmodels.HabitListViewModel
import com.dorokhov.hab.ui.viewmodels.ViewCycleViewModel
import com.dorokhov.hab.ui.viewmodels.ViewProgressViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateHabitViewModel::class)
    abstract fun bindCreateHabitViewModel(createHabitViewModel: CreateHabitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HabitListViewModel::class)
    abstract fun bindHabitListViewModel(habitListViewModel: HabitListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateCycleViewModel::class)
    abstract fun bindCreateCycleViewModel(createCycleViewModel: CreateCycleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewProgressViewModel::class)
    abstract fun bindViewProgressViewModel(viewProgressViewModel: ViewProgressViewModel): ViewModel

}