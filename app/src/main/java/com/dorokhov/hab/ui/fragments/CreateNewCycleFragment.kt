package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.fragments.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.create_new_cycle_layout.*

class CreateNewCycleFragment : BaseFragment() {

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
        dateInputLayout.setEndIconOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.AppTheme)
                .setTitleText("Начало цикла")
                .build()

            datePicker.addOnPositiveButtonClickListener { dateLong ->
                dateEditText.setText(dateLong.toString())
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_cycle_menu, menu)

    }
}