package com.github.johnnysc.spacex.presentation

import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.di.DI
import com.github.johnnysc.spacex.domain.Status
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 18.05.19
 */
class MainScreenViewModel : ViewModel() {

    private val interactor = DI.instance.getLaunchesInteractor()

    val searchState: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun fetch(query: String?) {
        viewModelScope.launch {
            when (interactor.fetch(query)) {
                Status.NO_RESULTS -> showScreenWithId(R.id.no_results)
                Status.NO_CONNECTION -> showScreenWithId(R.id.no_connection)
                Status.SERVICE_UNAVAILABLE -> showScreenWithId(R.id.service_unavailable)
                Status.SUCCESS -> showScreenWithId(R.id.search_results)
                Status.UNKNOWN -> showScreenWithId(R.id.start)
            }
        }
    }

    private fun showScreenWithId(@IdRes id: Int) {
        searchState.postValue(id)
    }
}