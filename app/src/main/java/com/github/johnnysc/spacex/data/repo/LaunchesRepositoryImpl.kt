package com.github.johnnysc.spacex.data.repo

import com.github.johnnysc.spacex.data.LaunchesDTO
import com.github.johnnysc.spacex.data.Year
import com.github.johnnysc.spacex.data.cache.CacheManager
import com.github.johnnysc.spacex.data.network.LaunchesService
import com.github.johnnysc.spacex.domain.LaunchesRepository

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesRepositoryImpl constructor(
    private val service: LaunchesService,
    private val cacheManager: CacheManager
) : BaseRepository(),
    LaunchesRepository {

    override suspend fun fetch(year: Year): List<LaunchesDTO>? {
        val launchesListForYear = cacheManager.getLaunchesForYears()[year]
        return if (launchesListForYear != null) {
            cacheManager.saveLastQuery(year)
            launchesListForYear
        } else {
            val launchesList =
                safeApiCall(errorMessage = "Error while fetching launches data") {
                    service.getLaunchesAsync(year).await()
                }

            if (launchesList != null && launchesList.isNotEmpty()) {
                cacheManager.saveLastQuery(year)
                val map = cacheManager.getLaunchesForYears().plus(year to launchesList)
                cacheManager.saveLaunchesForYears(map)
            }
            launchesList
        }
    }

    override suspend fun getLaunches(): List<LaunchesDTO> {
        val launches = cacheManager.getLaunchesForYears()
        val lastQuery = cacheManager.getLastQuery()
        return launches[lastQuery] ?: emptyList()
    }
}