package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.fragments.datastate.reason.ReasonStateEvent
import com.dorokhov.hab.ui.viewmodels.ReasonViewModel
import com.dorokhov.hab.utils.onTextChanged
import kotlinx.android.synthetic.main.reason_layout.*

class ReasonFragment : BaseFragment() {

    lateinit var reasonViewModel: ReasonViewModel
    private var taskId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskId = arguments?.getInt("taskId")
        if (taskId != null) {
            Toast.makeText(context, "${taskId} есть аргумент", Toast.LENGTH_SHORT).show()
        }
        return inflater.inflate(R.layout.reason_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reasonViewModel = ViewModelProvider(this, providerFactory)[ReasonViewModel::class.java]
        subscribeObserver()
        createCause.setOnClickListener {
            taskId?.let {
                reasonViewModel.setStateEvent(
                    ReasonStateEvent.AddReasonEvent(
                        it,
                        noteTextField.text.toString()
                    )
                )
            }
        }

        noteTextField.onTextChanged {symbols ->
            createCause.isEnabled = symbols > 0
        }
    }

    private fun subscribeObserver() {
        reasonViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            onDataStateChanged(dataState)
            dataState.data?.data?.getContentIfNotHandled()?.let {
                reasonViewModel.setViewState(it)
            }
        })

        reasonViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            noteTextField.setText(viewState.textReason)
            findNavController().popBackStack()
        })
    }

    override fun showLoadingState(visible: Boolean) {
        if (visible) horizontalProgressBar.visibility =
            View.VISIBLE else horizontalProgressBar.visibility = View.GONE
    }
}