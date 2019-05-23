package com.github.johnnysc.spacex.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.github.johnnysc.spacex.domain.interactor.search.SearchResultsInteractor
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsViewModel(
    application: Application,
    private val interactor: SearchResultsInteractor
) : AndroidViewModel(application) {

    val resultsLiveData = MutableLiveData<List<String>>()

    fun showResults() {
        viewModelScope.launch {
            resultsLiveData.value = interactor.getResults()
        }
    }

    class Factory(
        private val application: Application,
        private val searchResultsInteractor: SearchResultsInteractor
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            SearchResultsViewModel(application, searchResultsInteractor) as T
    }
}