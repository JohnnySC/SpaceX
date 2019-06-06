package com.github.johnnysc.spacex.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.johnnysc.spacex.di.MainScreenModule
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModelMapper
import kotlinx.coroutines.launch

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsViewModel : ViewModel() {

    val launchData = MutableLiveData<List<LaunchDataUiModel<*>>>()

    private val interactor = MainScreenModule.getLaunchDetailsInteractor()
    private val mapper = LaunchDataUiModelMapper()

    fun showData(year: String, position: Int?) = viewModelScope.launch {
        launchData.value = mapper.map(interactor.getLaunchData(year, position ?: 0))
    }
}