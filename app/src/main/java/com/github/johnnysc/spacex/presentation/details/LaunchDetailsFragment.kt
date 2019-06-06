package com.github.johnnysc.spacex.presentation.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.LaunchDetailsViewModel
import com.github.johnnysc.spacex.presentation.details.adapter.LaunchDetailsAdapter
import com.github.johnnysc.spacex.presentation.fragment.BaseFragment
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.fragment_launch_details.*
import java.util.*

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsFragment : BaseFragment(R.layout.fragment_launch_details) {

    companion object {
        const val EXTRA_YEAR = "extra_year"
        const val EXTRA_POSITION = "extra_position"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(EXTRA_POSITION, 0)
        val year = arguments?.getString(EXTRA_YEAR, Calendar.getInstance().get(Calendar.YEAR).toString())
        recyclerView.setHasFixedSize(true)
        val viewModel = activity?.run {
            ViewModelProviders.of(this).get(LaunchDetailsViewModel::class.java)
        }
        viewModel?.let { model ->
            model.launchData.observe(this, Observer<List<LaunchDataUiModel<*>>> {
                recyclerView.adapter = LaunchDetailsAdapter(it)
            })
            model.showData(year!!, position)
        }
    }
}