package com.dorokhov.hab.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dorokhov.hab.R
import com.dorokhov.hab.percistance.entities.Day
import com.dorokhov.hab.ui.fragments.ClickOnNoteButtonListener
import com.dorokhov.hab.ui.fragments.TaskCheckListener
import com.dorokhov.hab.ui.fragments.ViewProgressFragment
import com.dorokhov.hab.ui.fragments.ViewProgressFragment.Companion.CHECKED
import com.dorokhov.hab.ui.fragments.ViewProgressFragment.Companion.UNCHECKED
import com.dorokhov.hab.utils.isVisible
import com.dorokhov.hab.utils.visible
import kotlinx.android.synthetic.main.task_item_layout.view.*

const val UNKNOWN_RESULT = -1
const val UNKNOWN_ID = -1
const val INDETERMINATE_DATE = "00-00-2020"
const val UNNAMED = "unnamed"

class TaskRecyclerViewAdapter(
    private val taskCheckListener: TaskCheckListener,
    private val writeNoteButtonListener: ClickOnNoteButtonListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var taskList: List<Day>? = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        return TaskViewHolder(view, taskCheckListener, writeNoteButtonListener)
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
                    null,
                    UNKNOWN_RESULT
                )
            )
        }
    }

    inner class TaskViewHolder(
        view: View,
        val taskCheckListener: TaskCheckListener,
        val writeNoteButtonListener: ClickOnNoteButtonListener
    ) : RecyclerView.ViewHolder(view) {
        fun bind(day: Day) = with(itemView) {
            stateHabImageView.setOnClickListener {
                if (checkContainer.isVisible()) {
                    checkContainer.visible(false)
                } else {
                    checkContainer.visible(true)
                }
            }
            habName.text = day.habitName
            checkButton.setOnClickListener {
                day.dayId?.let { it1 ->
                    taskCheckListener.onCheck(it1, CHECKED)
                }
            }
            failButton.setOnClickListener {
                day.dayId?.let { it1 ->
                    taskCheckListener.onCheck(it1, UNCHECKED)
                    writeNoteButtonListener.navigateToNoteFragment(it1)
                }
            }
            skipButton.setOnClickListener {
                day.dayId?.let { it1 ->
                    taskCheckListener.onCheck(it1, ViewProgressFragment.INDETERMINATE)
                    writeNoteButtonListener.navigateToNoteFragment(it1)
                }
            }
        }
    }
}