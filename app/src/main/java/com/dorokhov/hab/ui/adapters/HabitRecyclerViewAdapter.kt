package com.dorokhov.hab.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dorokhov.hab.R
import com.dorokhov.hab.percistance.entities.Habit
import kotlinx.android.synthetic.main.habit_item_layout.view.*

class HabitRecyclerViewAdapter(
    private val onHabitClickListener: OnHabitClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var habitList: List<Habit>? = ArrayList()

    fun setHabits(habits: List<Habit>) {
        habitList = habits
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HabitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.habit_item_layout, parent, false),
            onHabitClickListener
        )
    }

    override fun getItemCount(): Int = habitList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HabitViewHolder) {
            holder.bind(habitList?.get(position))
        }
    }

    inner class HabitViewHolder(
        view: View,
        val onHabitClickListener: OnHabitClickListener?
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var habitId: Int? = null
        fun bind(habit: Habit?) = with(itemView) {
            habitId = habit?.habitId
            onHabitClickListener?.let {
                itemView.setOnClickListener(this@HabitViewHolder)
            }

            habit?.let {
                habitName.text = it.name
                habitDescription.text = it.description
                chipMonday.isChecked = it.monday
                chipTuesday.isChecked = it.tuesday
                chipWednesday.isChecked = it.wednesday
                chipThursday.isChecked = it.thursday
                chipFriday.isChecked = it.friday
                chipSaturday.isChecked = it.saturday
                chipSunday.isChecked = it.sunday
            }
        }

        override fun onClick(v: View?) {
            onHabitClickListener?.onHabitClick(habitId)
        }
    }
}

interface OnHabitClickListener {
    fun onHabitClick(id: Int?)
}