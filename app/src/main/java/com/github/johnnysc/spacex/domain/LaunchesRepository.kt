package com.github.johnnysc.spacex.domain

import com.github.johnnysc.spacex.data.LaunchesDTO

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesRepository {

    suspend fun fetch(year: String): MutableList<LaunchesDTO>?

    fun getLaunchesInCache(): List<LaunchesDTO>
}