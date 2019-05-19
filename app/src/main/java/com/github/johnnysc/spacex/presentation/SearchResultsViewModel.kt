package com.github.johnnysc.spacex.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.domain.SearchResultsInteractor

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsViewModel(application: Application) : AndroidViewModel(application) {

    val results = MutableLiveData<List<String>>()

    private val interactor: SearchResultsInteractor = (application as App).getDI().getSearchResultsInteractor()

    fun showResults() {
        results.value = interactor.getResults()
    }
}