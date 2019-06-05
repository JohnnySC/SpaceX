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

    override fun create(year: String, priority: LaunchesDataStoreFactory.Priority) =
        if (priority == LaunchesDataStoreFactory.Priority.CLOUD || !launchesCache.isCached(year))
            cloudLaunchesDataStore
        else
            diskLaunchesDataStore
}

interface LaunchesDataStoreFactory {

    enum class Priority {
        CLOUD,
        CACHE
    }

    fun create(year: String, priority: Priority): LaunchesDataStore
}