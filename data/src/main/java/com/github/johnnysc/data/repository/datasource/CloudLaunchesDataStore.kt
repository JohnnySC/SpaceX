package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.cache.LaunchesCache
import com.github.johnnysc.data.entity.LaunchesEntity
import com.github.johnnysc.domain.exception.NetworkConnectionException
import com.github.johnnysc.domain.exception.ServerUnavailableException
import com.github.johnnysc.data.net.ConnectionManager
import com.github.johnnysc.data.net.LaunchesService

/**
 * @author Asatryan on 26.05.19
 */
class CloudLaunchesDataStore(
    private val connectionManager: ConnectionManager,
    private val launchesService: LaunchesService,
    private val launchesCache: LaunchesCache
) : LaunchesDataStore {

    override suspend fun getLaunchEntityList(year: String): List<LaunchesEntity> {
        if (connectionManager.isNetworkAbsent()) {
            throw NetworkConnectionException()
        } else {
            val launchesAsync = launchesService.getLaunchesAsync(year)
            val launches: List<LaunchesEntity>?
            try {
                val result = launchesAsync.await()
                if (result.isSuccessful) {
                    launches = result.body()
                    if (launches == null)
                        throw ServerUnavailableException()
                    launchesCache.put(year, launches)
                } else {
                    throw ServerUnavailableException()
                }
            } catch (e: Exception) {
                throw NetworkConnectionException(e)
            }
            return launches
        }
    }

    override suspend fun getLaunchDetails(year: String, id: Int) =
        throw UnsupportedOperationException("Operation is not available")
}