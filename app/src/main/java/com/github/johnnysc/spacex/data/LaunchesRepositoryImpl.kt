package com.github.johnnysc.spacex.data

import com.github.johnnysc.spacex.data.cache.CacheManager
import com.github.johnnysc.spacex.data.network.LaunchesService
import com.github.johnnysc.spacex.domain.LaunchesRepository

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesRepositoryImpl(
    private val service: LaunchesService,
    private val cacheManager: CacheManager
) : BaseRepository(), LaunchesRepository {

    override suspend fun fetch(year: String): List<LaunchesDTO>? {
        val dataCache = cacheManager.getLaunches()
        if (dataCache.containsKey(year)) {
            cacheManager.saveLastQuery(year)
            return dataCache[year]
        }

        val response: List<LaunchesDTO>? = safeApiCall(
            call = { service.getLaunchesAsync(year).await() },
            errorMessage = "Error while fetching launches data"
        )

        if (response != null && response.isNotEmpty()) {
            cacheManager.saveLastQuery(year)
            val map: MutableMap<String, List<LaunchesDTO>> = cacheManager.getLaunches().toMutableMap()
            map[year] = response
            cacheManager.saveLaunches(map)
        }
        return response
    }

    override fun getLaunchesInCache(): List<LaunchesDTO> {
        val launches = cacheManager.getLaunches()
        val lastQuery = cacheManager.getLastQuery()
        return launches[lastQuery] ?: emptyList()
    }
}