package com.github.johnnysc.spacex.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.domain.LaunchData
import com.github.johnnysc.spacex.di.DI
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsViewModel : ViewModel() {

    val launchData = MutableLiveData<LaunchData>()

    private val interactor = DI.getLaunchDetailsInteractor()

    fun showData(year: String, position: Int?) = viewModelScope.launch {
        launchData.value = interactor.getLaunchData(year, position ?: 0)
    }
}