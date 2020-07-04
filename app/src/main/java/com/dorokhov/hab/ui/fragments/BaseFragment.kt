package com.dorokhov.hab.ui.fragments

import androidx.fragment.app.Fragment
import com.dorokhov.hab.utils.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory


}