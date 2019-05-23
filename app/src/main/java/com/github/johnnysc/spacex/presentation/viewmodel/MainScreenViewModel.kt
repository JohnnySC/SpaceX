package com.github.johnnysc.spacex.presentation.viewmodel

import android.app.Application
import androidx.annotation.IdRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.domain.interactor.launch.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 18.05.19
 */
class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    val screenIdLiveData = MutableLiveData<Int>()
    val progressStateLiveData = MutableLiveData<Boolean>()
    val errorStateLiveData = MutableLiveData<Int>()

    // TODO("Передавать Interactor из конструктор. Иначе это антипаттерн Service Loader (кажется так)")
    private val interactor = (application as App).di.getLaunchesInteractor()

    fun fetch(query: String) {
        viewModelScope.debounceLaunch(300) {
            val inputDataValid = interactor.isInputDataValid(query)
            if (inputDataValid == true) {
                progressStateLiveData.value = true
                when (interactor.fetch(query)) {
                    Status.NoResults -> showScreenWithId(R.id.no_results)
                    Status.NoConnection -> showScreenWithId(R.id.no_connection)
                    Status.ServiceUnavailable -> showScreenWithId(R.id.service_unavailable)
                    Status.Success -> showScreenWithId(R.id.go_to_search_results)
                }
            } else if (inputDataValid == false) {
                errorStateLiveData.value = R.string.invalid_input_message
            }
        }
    }

    private fun showScreenWithId(@IdRes id: Int) {
        progressStateLiveData.value = false
        screenIdLiveData.value = id
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
}