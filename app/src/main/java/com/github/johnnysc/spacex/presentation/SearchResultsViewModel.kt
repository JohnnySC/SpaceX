package com.github.johnnysc.spacex.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.spacex.App
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsViewModel(application: Application) : AndroidViewModel(application) {

    val results = MutableLiveData<List<String>>()

    private val interactor = (application as App).getDI().getSearchResultsInteractor()

    fun showResults(year: String) {
        viewModelScope.launch {
            results.value = interactor.getSearchResults(year).map { it.missionName }
        }
    }
}