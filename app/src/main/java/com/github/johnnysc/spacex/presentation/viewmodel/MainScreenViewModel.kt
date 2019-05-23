package com.github.johnnysc.spacex.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.data.Year
import com.github.johnnysc.spacex.domain.interactor.launch.LaunchesInteractor
import com.github.johnnysc.spacex.domain.interactor.launch.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 18.05.19
 */
class MainScreenViewModel(
    application: Application,
    private val interactor: LaunchesInteractor
) : AndroidViewModel(application) {

    val screenIdLiveData = MutableLiveData<Int>()
    val progressStateLiveData = MutableLiveData<Boolean>()
    val errorStateLiveData = MutableLiveData<Int>()

    fun fetch(query: Year) {
        viewModelScope.debounceLaunch(300) {
            val inputDataValid = interactor.isInputDataValid(query)
            if (inputDataValid != null)
                if (inputDataValid) {
                    progressStateLiveData.value = true
                    showScreenForStatus(interactor.fetch(query))
                } else {
                    errorStateLiveData.value = R.string.invalid_input_message
                }
        }
    }

    private fun showScreenForStatus(status: Status) {
        progressStateLiveData.value = false
        screenIdLiveData.value = when (status) {
            Status.NoResults -> R.id.no_results
            Status.NoConnection -> R.id.no_connection
            Status.ServiceUnavailable -> R.id.service_unavailable
            Status.Success -> R.id.go_to_search_results
        }
    }

    private var job: Job? = null

    private inline fun CoroutineScope.debounceLaunch(
        time: Long,
        crossinline block: suspend CoroutineScope.() -> Unit
    ): Job {
        job?.cancel()
        return launch {
            delay(time)
            block()
        }.also {
            job = it
        }
    }

    class Factory(
        private val application: Application,
        private val launchesInteractor: LaunchesInteractor
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainScreenViewModel(application, launchesInteractor) as T
    }
}