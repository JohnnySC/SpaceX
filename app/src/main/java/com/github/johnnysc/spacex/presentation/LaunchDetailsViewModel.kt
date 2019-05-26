package com.github.johnnysc.spacex.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.domain.LaunchData
import com.github.johnnysc.spacex.App
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val launchData = MutableLiveData<LaunchData>()

    private val interactor = (application as App).getDI().getLaunchDetailsInteractor()

    fun showData(year: String, position: Int?) {
        viewModelScope.launch {
            launchData.value = interactor.getLaunchData(year, position ?: 0)
        }
    }
}