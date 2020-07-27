package com.dorokhov.hab.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dorokhov.hab.R
import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.ui.fragments.TaskCheckListener
import com.dorokhov.hab.ui.fragments.ViewProgressFragment.Companion.CHECKED
import com.dorokhov.hab.ui.fragments.ViewProgressFragment.Companion.INDETERMINATE
import com.dorokhov.hab.ui.fragments.ViewProgressFragment.Companion.UNCHECKED
import com.dorokhov.hab.utils.Logger
import kotlinx.android.synthetic.main.task_item_layout.view.*

const val UNKNOWN_RESULT = -1
const val UNKNOWN_ID = -1
const val INDETERMINATE_DATE = "00-00-2020"
const val UNNAMED = "unnamed"

class TaskRecyclerViewAdapter(
    val taskCheckListener: TaskCheckListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var taskList: List<Day>? = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        return TaskViewHolder(view, taskCheckListener)
    }

    override fun getItemCount(): Int = taskList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TaskViewHolder) {
            taskList?.get(position)?.let { day ->
                holder.bind(day)
            } ?: holder.bind(
                Day(
                    UNKNOWN_ID,
                    UNKNOWN_ID,
                    UNNAMED,
                    INDETERMINATE_DATE,
                    UNKNOWN_RESULT
                )
            )
        }
    }

    inner class TaskViewHolder(
        view: View,
        val taskCheckListener: TaskCheckListener
    ) : RecyclerView.ViewHolder(view) {
        fun bind(day: Day) = with(itemView) {
            habName.text = day.habitName
            habState.setOnClickListener {
                when {
                    !habState.isChecked && !habState.isIndeterminate -> {
                        taskCheckListener.onCheck(day.dayId!!, UNCHECKED)
                    }
                    habState.isIndeterminate -> {
                        taskCheckListener.onCheck(day.dayId!!,INDETERMINATE)
                    }
                    habState.isChecked -> {
                        taskCheckListener.onCheck(day.dayId!!, CHECKED)
                    }
                }
            }
        }
    }
}