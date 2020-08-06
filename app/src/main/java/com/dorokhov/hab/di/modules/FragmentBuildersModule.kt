package com.dorokhov.hab.di.modules

import com.dorokhov.hab.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeAddHabitFromListFragment(): AddHabitFromListOfHabitsFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateNewCycleFragment(): CreateNewCycleFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateNewHabitFragment(): CreateNewHabitFragment

    @ContributesAndroidInjector
    abstract fun contributeEditCycleFragment(): EditCycleFragment

    @ContributesAndroidInjector
    abstract fun contributeEditHabitFragment(): EditHabitFragment

    @ContributesAndroidInjector
    abstract fun contributeViewProgressFragment(): ViewProgressFragment

    @ContributesAndroidInjector
    abstract fun contributeReasonFragment(): ReasonFragment
}