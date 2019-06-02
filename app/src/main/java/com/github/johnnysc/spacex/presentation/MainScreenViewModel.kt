package com.github.johnnysc.spacex.presentation

import android.app.Application
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.domain.interactor.Status
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.fragment.SearchResultsFragment.Companion.EXTRA_YEAR
import kotlinx.coroutines.*

/**
 * @author Asatryan on 18.05.19
 */
class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    val searchState = MutableLiveData<Pair<Int, Bundle>>()
    val progressState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Int>()

    private val interactor = (application as App).getDI().getLaunchesInteractor()
    private var job: Job? = null
    private var lastQuery: String? = null

    fun fetch(query: String?) {
        lastQuery = query
        viewModelScope.debounceLaunch(300) {
            val inputDataValid = interactor.isInputDataValid(query)
            if (inputDataValid == true) {
                progressState.postValue(true)
                when (interactor.fetch(query!!)) {
                    Status.NO_RESULTS -> showScreenWithId(R.id.no_results)
                    Status.NO_CONNECTION -> showScreenWithId(R.id.no_connection)
                    Status.SERVICE_UNAVAILABLE -> showScreenWithId(R.id.service_unavailable)
                    Status.SUCCESS -> showScreenWithId(R.id.go_to_search_results)
                }
            } else if (inputDataValid == false) {
                errorState.postValue(R.string.invalid_input_message)
            }
        }
    }

    private fun showScreenWithId(@IdRes id: Int) {
        progressState.postValue(false)
        searchState.postValue(Pair(id, Bundle().apply { putString(EXTRA_YEAR, lastQuery) }))
    }

    private fun CoroutineScope.debounceLaunch(
        time: Long,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        job?.cancel()
        return launch() {
            delay(time)
            block()
        }.also {
            job = it
        }
    }
}