package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.fragments.BaseFragment
import com.dorokhov.hab.ui.fragments.datastate.editcycle.EditCycleStateEvent
import com.dorokhov.hab.ui.viewmodels.EditCycleViewModel
import com.dorokhov.hab.utils.Logger
import kotlinx.android.synthetic.main.edit_cycle_layout.*

class EditCycleFragment: BaseFragment() {

    lateinit var editCycleViewModel: EditCycleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_cycle_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editCycleViewModel = ViewModelProvider(this, providerFactory)[EditCycleViewModel::class.java]
        subscribeObserver()
        editCycleViewModel.setStateEvent(EditCycleStateEvent.GetCycleInfoWithHabits())
    }

    private fun subscribeObserver() {
        editCycleViewModel.dataState.observe(viewLifecycleOwner, Observer {dataState ->
            onDataStateChanged(dataState)
            dataState.data?.data?.getContentIfNotHandled()?.let { editCycleViewState ->
                editCycleViewModel.setViewState(editCycleViewState)
            }
        })

        editCycleViewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            nameEditText.setText(viewState.cycleInfoFields.nameCycle)
            Logger.loge(viewState)
        })
    }

    override fun showLoadingState(visible: Boolean) {

    }

}