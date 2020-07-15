package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.CommonProgressFields
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressStateEvent
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import com.dorokhov.hab.ui.viewmodels.ViewProgressViewModel
import kotlinx.android.synthetic.main.view_progress_layout.*

class ViewProgressFragment : BaseFragment() {

    lateinit var viewProgressViewModel: ViewProgressViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_progress_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewProgressViewModel =
            ViewModelProvider(this, providerFactory)[ViewProgressViewModel::class.java]
        subscribeObserver()
        viewProgressViewModel.setStateEvent(ViewProgressStateEvent.RequestCommonInformation("21-05-2020"))
        addNewCycle.setOnClickListener {
            findNavController().navigate(R.id.action_viewProgressFragment_to_createNewCycleFragment)
        }
        editCycle.setOnClickListener {

        }
    }

    private fun subscribeObserver() {
        viewProgressViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            onDataStateChanged(dataState)
            dataState.data?.data?.getContentIfNotHandled()?.let {viewState ->
                viewProgressViewModel.setViewState(viewState)
            }
        })

        viewProgressViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState.listOfTask.taskList.isNotEmpty()) {
                // todo set to list
            }
            attemptToSetContent(viewState.commonProgressFields)
        })
    }

    private fun attemptToSetContent(commonInfo: CommonProgressFields) {
        nameCycle.text = commonInfo.nameOfTheCycle
        amountOfHabits.text = commonInfo.amountOfHabits
        daysToEnd.text = commonInfo.daysToTheEndOfTheCycle
    }

    override fun showLoadingState(visible: Boolean) {
        if (visible) horizontalProgressBar.visibility =
            View.VISIBLE else horizontalProgressBar.visibility = View.GONE
    }

}