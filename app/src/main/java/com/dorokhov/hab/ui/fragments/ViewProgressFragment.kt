package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.adapters.TaskRecyclerViewAdapter
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.CommonProgressFields
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressStateEvent
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import com.dorokhov.hab.ui.viewmodels.ViewProgressViewModel
import com.dorokhov.hab.utils.ErrorCodes
import com.dorokhov.hab.utils.Logger
import kotlinx.android.synthetic.main.view_progress_layout.*

class ViewProgressFragment : BaseFragment(), TaskCheckListener {

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
        viewProgressViewModel.setStateEvent(ViewProgressStateEvent.RequestCommonInformation("27-07-2020"))
        subscribeObserver()
        addNewCycle.setOnClickListener {
            findNavController().navigate(R.id.action_viewProgressFragment_to_createNewCycleFragment)
        }
        editCycle.setOnClickListener {

        }
    }

    private fun subscribeObserver() {
        viewProgressViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            handleErrorCode(dataState)
            onDataStateChanged(dataState)
            dataState.data?.data?.getContentIfNotHandled()?.let { viewState ->
                viewProgressViewModel.setViewState(viewState)
            }
        })

        viewProgressViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState.listOfTaskFields.taskList.isNotEmpty()) {
                Logger.loge("ViewProgressFragment ${viewState}")
                listHabitsInCurrentDay.apply {
                    adapter = TaskRecyclerViewAdapter(this@ViewProgressFragment).apply {
                        taskList = viewState.listOfTaskFields.taskList
                    }
                    layoutManager = LinearLayoutManager(context)
                }
            }
            attemptToSetContent(viewState.commonProgressFields)
        })
    }

    private fun handleErrorCode(dataState: DataState<ViewProgressViewState>?) {
        dataState?.data?.response?.peekContent()?.let { responseInfo ->
            Logger.loge(responseInfo.codeError)
            when (responseInfo.codeError) {
                ErrorCodes.THERE_IS_NOT_CYCLE -> {
                    findNavController().navigate(R.id.action_viewProgressFragment_to_createNewCycleFragment)
                }
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onCheck(dayId: Int, state: Int) {
        Logger.loge("onCheckBox change state: ${dayId} ${state}")
    }

    companion object {
        const val UNCHECKED: Int = 0
        const val INDETERMINATE = 1
        const val CHECKED = 2
    }
}

public interface TaskCheckListener {
    fun onCheck(dayId: Int, state: Int)
}

