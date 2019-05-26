package com.github.johnnysc.domain.interactor

import com.github.johnnysc.domain.LaunchData

/**
 * @author Asatryan on 19.05.19
 */
interface LaunchDetailsInteractor {

    suspend fun getLaunchData(year: String, position: Int): LaunchData
}