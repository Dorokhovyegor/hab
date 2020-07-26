package com.dorokhov.hab.di.modules

import com.dorokhov.hab.ui.MainActivity
import com.dorokhov.hab.di.scopes.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class, ViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}