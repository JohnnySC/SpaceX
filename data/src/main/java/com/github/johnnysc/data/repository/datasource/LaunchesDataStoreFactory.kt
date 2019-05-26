package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.cache.LaunchesCache

/**
 * @author Asatryan on 26.05.19
 */
class LaunchesDataStoreFactory(
    private val launchesCache: LaunchesCache,
    private val diskLaunchesDataStore: DiskLaunchesDataStore,
    private val cloudLaunchesDataStore: CloudLaunchesDataStore
) {

    fun create(year: String): LaunchesDataStore {
        return if (launchesCache.isCached(year))
            diskLaunchesDataStore
        else
            cloudLaunchesDataStore
    }
}