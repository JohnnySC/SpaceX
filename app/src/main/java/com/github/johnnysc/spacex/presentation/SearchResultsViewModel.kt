package com.github.johnnysc.spacex.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.johnnysc.spacex.di.DI
import com.github.johnnysc.spacex.domain.SearchResultsInteractor

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsViewModel : ViewModel() {

    val results = MutableLiveData<List<String>>()

    private val interactor: SearchResultsInteractor = DI.instance.getSearchResultsInteractor()

    fun showResults() {
        results.value = interactor.getResults()
    }
}