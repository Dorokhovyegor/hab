package com.dorokhov.hab.di.components

import android.app.Application
import com.dorokhov.hab.HabitApp
import com.dorokhov.hab.di.modules.ActivityBuildersModule
import com.dorokhov.hab.di.modules.AppModule
import com.dorokhov.hab.di.modules.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent: AndroidInjector<HabitApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

}