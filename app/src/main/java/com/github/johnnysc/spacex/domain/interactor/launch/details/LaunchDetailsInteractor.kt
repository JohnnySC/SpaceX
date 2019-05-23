package com.github.johnnysc.spacex.domain.interactor.launch.details

import com.github.johnnysc.spacex.domain.entity.LaunchData

/**
 * @author Asatryan on 19.05.19
 */
interface LaunchDetailsInteractor {

    suspend fun getLaunchData(position: Int): LaunchData
}