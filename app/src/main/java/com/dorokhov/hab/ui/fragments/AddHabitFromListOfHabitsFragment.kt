package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.adapters.HabitRecyclerViewAdapter
import com.dorokhov.hab.ui.fragments.datastate.ViewHabitStateEvent
import com.dorokhov.hab.ui.viewmodels.HabitListViewModel
import kotlinx.android.synthetic.main.list_of_habits_layout.*

class AddHabitFromListOfHabitsFragment : BaseFragment() {

    lateinit var habitListViewModel: HabitListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_habits_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        habitListViewModel = ViewModelProvider(this, providerFactory)[HabitListViewModel::class.java]
        habitListViewModel.setStateEvent(ViewHabitStateEvent.GetAllHabits())
        subscribeObserver()
    }


    private fun subscribeObserver() {
        habitListViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // handle changing datastate
            dataState?.data?.data?.getContentIfNotHandled()?.let { viewState ->
                habitListViewModel.setViewState(viewState)
            }
        })

        habitListViewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            val adapterRecView = HabitRecyclerViewAdapter()
            adapterRecView.setHabits(viewState.habitFields.habits)

            habitsRecyclerView?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterRecView
            }
        })
    }

}