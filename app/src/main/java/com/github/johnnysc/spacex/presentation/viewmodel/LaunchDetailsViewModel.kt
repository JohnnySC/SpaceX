package com.github.johnnysc.spacex.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.domain.entity.LaunchData

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val launchData = MutableLiveData<LaunchData>()

    // TODO("Передавать Interactor из конструктор. Иначе это антипаттерн Service Loader (кажется так)")
    private val interactor = (application as App).di.getLaunchDetailsInteractor()

    fun showData(position: Int) {
        launchData.value = interactor.getLaunchData(position)
    }
}