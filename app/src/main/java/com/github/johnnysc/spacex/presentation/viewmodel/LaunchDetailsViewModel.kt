package com.github.johnnysc.spacex.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.github.johnnysc.spacex.domain.entity.LaunchData
import com.github.johnnysc.spacex.domain.interactor.launch.details.LaunchDetailsInteractor
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsViewModel(
    application: Application,
    private val interactor: LaunchDetailsInteractor
) : AndroidViewModel(application) {

    val launchData = MutableLiveData<LaunchData>()

    fun showData(position: Int) {
        viewModelScope.launch {
            launchData.value = interactor.getLaunchData(position)
        }
    }

    class Factory(
        private val application: Application,
        private val launchDetailsInteractor: LaunchDetailsInteractor
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            LaunchDetailsViewModel(application, launchDetailsInteractor) as T
    }
}