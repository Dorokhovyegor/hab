package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.fragments.datastate.createcycle.CreateCycleStateEvent
import com.dorokhov.hab.ui.viewmodels.CreateCycleViewModel
import com.dorokhov.hab.utils.toStringDate
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.create_new_cycle_layout.*

class CreateNewCycleFragment : BaseFragment() {

    lateinit var createCycleViewModel: CreateCycleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.create_new_cycle_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createCycleViewModel =
            ViewModelProvider(this, providerFactory)[CreateCycleViewModel::class.java]
        subscribeObserver()
        dateInputLayout.setEndIconOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.AppTheme)
                .setTitleText("Начало цикла")
                .build()

            datePicker.addOnPositiveButtonClickListener { dateLong ->
                dateEditText.setText(dateLong.toStringDate())
            }

            datePicker.show(childFragmentManager, "datePicker")
        }

        addNewHabit.setOnClickListener {
            findNavController().navigate(R.id.action_createNewCycleFragment_to_createNewHabitFragment)
        }

        addHabitFromListOfHabits.setOnClickListener {
            findNavController().navigate(R.id.action_createNewCycleFragment_to_addHabitFromListOfHabits)
        }
    }

    private fun subscribeObserver() {
        createCycleViewModel.dataState.observe(viewLifecycleOwner, Observer { dateState ->
            onDataStateChanged(dateState)
            dateState.data?.data?.getContentIfNotHandled()?.let {viewState ->
                createCycleViewModel.setViewState(viewState)
            }
        })

        createCycleViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

        })
    }

    override fun showLoadingState(visible: Boolean) {
        if (visible) horizontalProgressBar.visibility = View.VISIBLE else horizontalProgressBar.visibility =
            View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_cycle_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.createCycle -> {
            createCycleViewModel.setStateEvent(
                CreateCycleStateEvent.CreateCycleEvent(
                    nameEditText.text.toString(),
                    dateEditText.text.toString()
                )
            )
            true
        }
        else -> {
            false
        }
    }

}