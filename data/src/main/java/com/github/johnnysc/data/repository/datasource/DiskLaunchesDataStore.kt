package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.cache.LaunchesCache

/**
 * @author Asatryan on 26.05.19
 */
class DiskLaunchesDataStore(
    private val launchesCache: LaunchesCache
) : LaunchesDataStore {

    override suspend fun getLaunchEntityList(year: String) =
        launchesCache.get(year)

    override suspend fun getLaunchDetails(year: String, id: Int) =
        getLaunchEntityList(year)[id]
}