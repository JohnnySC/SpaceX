package com.github.johnnysc.spacex.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.johnnysc.spacex.App

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsViewModel(application: Application) : AndroidViewModel(application) {

    val resultsLiveData = MutableLiveData<List<String>>()

    // TODO("Передавать Interactor из конструктор. Иначе это антипаттерн Service Loader (кажется так)")
    private val interactor = (application as App).di.getSearchResultsInteractor()

    fun showResults() {
        resultsLiveData.value = interactor.getResults()
    }
}