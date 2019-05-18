package com.github.johnnysc.spacex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Asatryan on 18.05.19
 */
class MainScreenViewModel : ViewModel() {

    val searchState: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun fetch(query: String?) {
        //todo go to interactor with query
    }

    private fun showNoResults() {
        searchState.value = R.id.no_results
    }

    private fun showNoConnection() {
        searchState.value = R.id.no_connection
    }

    private fun showSearchResults() {
        searchState.value = R.id.search_results
    }

    private fun showServiceUnavailable() {
        searchState.value = R.id.service_unavailable
    }
}