package com.dorokhov.hab.di.modules

import androidx.lifecycle.ViewModelProvider
import com.dorokhov.hab.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

}