package com.github.johnnysc.spacex.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.spacex.di.DI
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsViewModel : ViewModel() {

    val results = MutableLiveData<List<String>>()

    private val interactor = DI.getSearchResultsInteractor()

    fun showResults(year: String) = viewModelScope.launch {
        results.value = interactor.getSearchResults(year).map { it.missionName }
    }
}