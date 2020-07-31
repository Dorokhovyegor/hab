package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dorokhov.hab.R
import com.dorokhov.hab.ui.adapters.HabitRecyclerViewAdapter
import com.dorokhov.hab.ui.fragments.datastate.editcycle.EditCycleStateEvent
import com.dorokhov.hab.ui.viewmodels.EditCycleViewModel
import kotlinx.android.synthetic.main.edit_cycle_layout.*

class EditCycleFragment : BaseFragment() {

    lateinit var editCycleViewModel: EditCycleViewModel
    var adapter: HabitRecyclerViewAdapter = HabitRecyclerViewAdapter(null).apply {
        setHasStableIds(true)
    }

    private var itemTouchHelper: ItemTouchHelper.SimpleCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (viewHolder is HabitRecyclerViewAdapter.HabitViewHolder) {
                adapter.deleteHabit(viewHolder.adapterPosition)
                editCycleViewModel.setStateEvent(EditCycleStateEvent.DeleteHabitFromCycle(viewHolder.itemId.toInt()))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.edit_cycle_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewHabit.setOnClickListener {
            findNavController().navigate(R.id.action_editCycleFragment_to_createNewHabitFragment)
        }
        editCycleViewModel = ViewModelProvider(this, providerFactory)[EditCycleViewModel::class.java]
        editCycleViewModel.setStateEvent(EditCycleStateEvent.GetCycleInfoWithHabits())
        subscribeObserver()
    }

    private fun subscribeObserver() {
        editCycleViewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            onDataStateChanged(dataState)
            dataState.data?.data?.getContentIfNotHandled()?.let { editCycleViewState ->
                editCycleViewModel.setViewState(editCycleViewState)
            }
        })

        editCycleViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            nameEditText.setText(viewState.cycleInfoFields.nameCycle)
            if (viewState.habitListFields.habitList.isNotEmpty()) {
                listHabitsInCurrentCycle.apply {
                    adapter = this@EditCycleFragment.adapter
                    this@EditCycleFragment.adapter.habitList = viewState.habitListFields.habitList
                    layoutManager = LinearLayoutManager(context)
                }
                ItemTouchHelper(itemTouchHelper).attachToRecyclerView(listHabitsInCurrentCycle)
            }
        })
    }

    override fun showLoadingState(visible: Boolean) {
        if (visible) horizontalProgressBar.visibility =
            View.VISIBLE else horizontalProgressBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_cycle_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.editCycle -> {
            editCycleViewModel.setStateEvent(EditCycleStateEvent.SaveName(nameEditText.text.toString()))
            true
        }
        R.id.deleteCycle -> {
            editCycleViewModel.setStateEvent(EditCycleStateEvent.ResetCycle())
            true
        }
        else -> {
            false
        }
    }



}