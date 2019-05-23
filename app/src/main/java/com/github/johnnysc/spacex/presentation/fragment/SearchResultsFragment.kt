package com.github.johnnysc.spacex.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.getViewModel
import com.github.johnnysc.spacex.observe
import com.github.johnnysc.spacex.presentation.viewmodel.SearchResultsViewModel
import com.github.johnnysc.spacex.update
import kotlinx.android.synthetic.main.fragment_search_results.*
import org.koin.android.ext.android.get

/**
 * @author Asatryan on 18.05.19
 */
class SearchResultsFragment : BaseFragment(R.layout.fragment_search_results) {
    private val arrayAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, mutableListOf())
    }

    private val viewModel: SearchResultsViewModel by lazy {
        getViewModel<SearchResultsViewModel>(
            SearchResultsViewModel.Factory(get(), get())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.resultsLiveData.observe(this, arrayAdapter::update)
        searchResultsListView.run {
            adapter = arrayAdapter
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                Navigation
                    .findNavController(view)
                    .navigate(
                        R.id.details_screen,
                        bundleOf(LaunchDetailsFragment.EXTRA_POSITION to position)
                    )
            }
        }
        viewModel.showResults()
    }
}