package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.fragments.datastate.viewcycleinfo.ViewCycleStateEvent
import com.dorokhov.hab.ui.fragments.datastate.viewcycleinfo.ViewCycleViewState
import com.dorokhov.hab.ui.viewmodels.ViewCycleViewModel
import com.dorokhov.hab.utils.getRandomCell
import kotlinx.android.synthetic.main.view_progress_layout.*

class ViewProgressFragment : BaseFragment() {

    val TAG = "YegorDebug"

    lateinit var viewCycleViewModel: ViewCycleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_progress_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCycleViewModel = ViewModelProvider(this, providerFactory)[ViewCycleViewModel::class.java]
        addNewCycle.setOnClickListener {
            findNavController().navigate(R.id.action_viewProgressFragment_to_createNewCycleFragment)
        }
        subscribeObserver()
        viewCycleViewModel.setStateEvent(ViewCycleStateEvent.GetFullInfoAboutThisCycleInCurrentDay())
    }

    private fun subscribeObserver() {
        viewCycleViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            onDataStateChanged(dataState)
            println("$TAG: subscribeObserver ${dataState.data.toString()}")
            dataState.data?.data?.getContentIfNotHandled()?.let { viewCycleViewState ->
                viewCycleViewModel.setViewState(viewCycleViewState)
            }
        })

        viewCycleViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

        })
    }

    override fun showLoadingState(visible: Boolean) {
        if (visible) horizontalProgressBar.visibility =
            View.VISIBLE else horizontalProgressBar.visibility = View.GONE
    }

}