package com.github.johnnysc.spacex.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.domain.LaunchData

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val launchData = MutableLiveData<LaunchData>()

    private val interactor = (application as App).getDI().getLaunchDetailsInteractor()

    fun showData(position: Int?) {
        launchData.value = interactor.getLaunchData(position ?: 0)
    }
}