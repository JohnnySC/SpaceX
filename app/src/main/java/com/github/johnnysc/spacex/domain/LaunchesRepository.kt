package com.github.johnnysc.spacex.domain

import com.github.johnnysc.spacex.data.LaunchesDTO
import com.github.johnnysc.spacex.data.Year

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesRepository {

    suspend fun fetch(year: Year): List<LaunchesDTO>?

    fun getLaunchesInCache() : List<LaunchesDTO>
}