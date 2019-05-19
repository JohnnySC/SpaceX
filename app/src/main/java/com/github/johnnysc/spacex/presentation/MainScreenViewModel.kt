package com.github.johnnysc.spacex.presentation

import android.app.Application
import androidx.annotation.IdRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.domain.Status
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 18.05.19
 */
class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val interactor = (application as App).getDI().getLaunchesInteractor()

    val searchState = MutableLiveData<Int>()
    val progressState = MutableLiveData<Boolean>()

    fun fetch(query: String?) {
        progressState.value = true
        viewModelScope.launch {
            when (interactor.fetch(query)) {
                Status.NO_RESULTS -> showScreenWithId(R.id.no_results)
                Status.NO_CONNECTION -> showScreenWithId(R.id.no_connection)
                Status.SERVICE_UNAVAILABLE -> showScreenWithId(R.id.service_unavailable)
                Status.SUCCESS -> showScreenWithId(R.id.go_to_search_results)
                Status.UNKNOWN -> progressState.postValue(false)
            }
        }
    }

    private fun showScreenWithId(@IdRes id: Int) {
        progressState.postValue(false)
        searchState.postValue(id)
    }
}