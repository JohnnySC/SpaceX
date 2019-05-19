package com.github.johnnysc.spacex.data.cache

import com.github.johnnysc.spacex.data.LaunchesDTO

/**
 * @author Asatryan on 19.05.19
 */
interface CacheManager {

    companion object {
        const val LAUNCHES = "launches"
        const val LAST_QUERY = "last_query"
    }

    fun saveLaunches(launches: Map<String, List<LaunchesDTO>>)

    fun getLaunches(): MutableMap<String, List<LaunchesDTO>>

    fun saveLastQuery(query: String)

    fun getLastQuery(): String
}