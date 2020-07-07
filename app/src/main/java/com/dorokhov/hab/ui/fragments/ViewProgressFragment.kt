package com.dorokhov.hab.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dorokhov.hab.R
import com.dorokhov.hab.utils.getRandomCell
import kotlinx.android.synthetic.main.view_progress_layout.*

class ViewProgressFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_progress_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewCycle.setOnClickListener {
            findNavController().navigate(R.id.action_viewProgressFragment_to_createNewCycleFragment)
        }

    }

    override fun showLoadingState(visible: Boolean) {
        if (visible) horizontalProgressBar.visibility =
            View.VISIBLE else horizontalProgressBar.visibility = View.GONE
    }
}