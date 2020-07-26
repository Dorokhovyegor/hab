package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.View
import com.dorokhov.hab.ui.ChangeDataStateListener
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.utils.Logger
import com.dorokhov.hab.utils.ViewModelProviderFactory
import com.dorokhov.hab.utils.showShortToast
import dagger.android.support.DaggerFragment
import javax.inject.Inject
abstract class BaseFragment : DaggerFragment(), ChangeDataStateListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onDataStateChanged(dataState: DataState<*>) {
        dataState.data?.response?.getContentIfNotHandled()?.let {
            it.message?.let { msg ->
                context?.showShortToast(it.message)
            }
        }
        showLoadingState(dataState.loading.isLoading)
    }

    abstract fun showLoadingState(visible: Boolean)
}

