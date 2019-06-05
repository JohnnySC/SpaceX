package com.github.johnnysc.domain.repository

import com.github.johnnysc.domain.LaunchData


/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesRepository {

    suspend fun getLaunches(year: String, reload: Boolean = false): List<LaunchData>

    suspend fun getLaunchData(year: String, id: Int): LaunchData
}