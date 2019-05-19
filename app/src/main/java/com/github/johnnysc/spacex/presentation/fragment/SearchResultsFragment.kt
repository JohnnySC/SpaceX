package com.github.johnnysc.spacex.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.SearchResultsViewModel
import kotlinx.android.synthetic.main.fragment_search_results.*

/**
 * @author Asatryan on 18.05.19
 */
class SearchResultsFragment : BaseFragment(R.layout.fragment_search_results) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = activity?.run {
            ViewModelProviders.of(this).get(SearchResultsViewModel::class.java)
        }
        viewModel?.let { model ->
            model.results.observe(this, Observer<List<String>> {
                searchResultsListView.apply {
                    adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, it)
                    onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        Navigation.findNavController(view).navigate(R.id.details_screen, Bundle().apply {
                            putInt(LaunchDetailsFragment.EXTRA_POSITION, position)
                        })
                    }
                }
            })
            model.showResults()
        }
    }
}