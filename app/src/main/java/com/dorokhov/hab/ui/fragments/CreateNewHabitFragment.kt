package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.fragments.datastate.createhabit.CreateNewHabitStateEvent
import com.dorokhov.hab.ui.viewmodels.CreateHabitViewModel
import kotlinx.android.synthetic.main.create_new_habit_layout.*

class CreateNewHabitFragment : BaseFragment() {

    lateinit var createNewHabitViewModel: CreateHabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_new_habit_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNewHabitViewModel =
            ViewModelProvider(this, providerFactory)[CreateHabitViewModel::class.java]
        initListeners()
        subscribeObserver()
    }

    private fun initListeners() {
        createHabitButton.setOnClickListener {
            createNewHabitViewModel.setStateEvent(
                CreateNewHabitStateEvent.CreateHabitEvent(
                nameHabitEditText.text.toString(),
                descriptionEditText.text.toString(),
                chipMonday.isChecked,
                chipTuesday.isChecked,
                chipWednesday.isChecked,
                chipThursday.isChecked,
                chipFriday.isChecked,
                chipSaturday.isChecked,
                chipSunday.isChecked
            ))
        }
    }

    private fun subscribeObserver() {
        createNewHabitViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // state change listener

        })
    }
}