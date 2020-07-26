package com.dorokhov.hab.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dorokhov.hab.percistance.entities.Day

class TaskRecyclerViewAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var taskList: List<Day>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        
    }

    override fun getItemCount(): Int = taskList?.size?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(day: Day) = with(itemView) {


        }

    }
}