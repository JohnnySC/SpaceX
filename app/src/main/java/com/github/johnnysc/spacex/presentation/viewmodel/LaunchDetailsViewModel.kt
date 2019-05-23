package com.github.johnnysc.spacex.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.johnnysc.spacex.domain.entity.LaunchData
import com.github.johnnysc.spacex.domain.interactor.launch.details.LaunchDetailsInteractor

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsViewModel(
    application: Application,
    private val interactor: LaunchDetailsInteractor
) : AndroidViewModel(application) {

    val launchData = MutableLiveData<LaunchData>()

    fun showData(position: Int) {
        launchData.value = interactor.getLaunchData(position)
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