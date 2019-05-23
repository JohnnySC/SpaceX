package com.github.johnnysc.spacex.data.cache

import com.github.johnnysc.spacex.data.LaunchesDTO
import com.github.johnnysc.spacex.data.Year

/**
 * @author Asatryan on 19.05.19
 */
interface CacheManager {

    companion object {
        const val LAUNCHES = "launches"
        const val LAST_QUERY = "last_query"
    }

    fun saveLaunchesForYears(launches: Map<Year, List<LaunchesDTO>>)

    fun getLaunchesForYears(): Map<Year, List<LaunchesDTO>>

    fun saveLastQuery(query: String)

    fun getLastQuery(): String
}