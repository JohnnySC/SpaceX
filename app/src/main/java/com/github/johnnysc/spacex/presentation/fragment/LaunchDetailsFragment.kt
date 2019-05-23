package com.github.johnnysc.spacex.presentation.fragment

import android.os.Bundle
import android.view.View
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.getViewModel
import com.github.johnnysc.spacex.observe
import com.github.johnnysc.spacex.presentation.viewmodel.LaunchDetailsViewModel
import kotlinx.android.synthetic.main.fragment_launch_details.*
import org.koin.android.ext.android.get

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsFragment : BaseFragment(R.layout.fragment_launch_details) {
    private val viewModel: LaunchDetailsViewModel by lazy {
        getViewModel<LaunchDetailsViewModel>(
            LaunchDetailsViewModel.Factory(get(), get())
        )
    }

    companion object {
        const val EXTRA_POSITION = "extra_position"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.launchData.observe(this) {
            textView.text = it.toString() //just to check all the data was parsed good
            //rootRecyclerView.adapter TODO set adapter with data
        }

        val position = arguments?.getInt(EXTRA_POSITION, 0) ?: 0
        viewModel.showData(position)
    }
}