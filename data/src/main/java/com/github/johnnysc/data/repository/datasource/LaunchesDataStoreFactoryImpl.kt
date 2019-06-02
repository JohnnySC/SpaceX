package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.cache.LaunchesCache

/**
 * @author Asatryan on 26.05.19
 */
class LaunchesDataStoreFactoryImpl(
    private val launchesCache: LaunchesCache,
    private val diskLaunchesDataStore: DiskLaunchesDataStore,
    private val cloudLaunchesDataStore: CloudLaunchesDataStore
) : LaunchesDataStoreFactory {

    override fun create(year: String) =
        if (launchesCache.isCached(year))
            diskLaunchesDataStore
        else
            cloudLaunchesDataStore
}

interface LaunchesDataStoreFactory {

    fun create(year: String): LaunchesDataStore
}