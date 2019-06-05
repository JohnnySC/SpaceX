package com.github.johnnysc.data.repository

import com.github.johnnysc.data.entity.mapper.LaunchDataMapper
import com.github.johnnysc.data.repository.datasource.LaunchesDataStoreFactory
import com.github.johnnysc.domain.LaunchData
import com.github.johnnysc.domain.repository.LaunchesRepository

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesRepositoryImpl(
    private val launchesDataStoreFactory: LaunchesDataStoreFactory,
    private val launchDataMapper: LaunchDataMapper
) : LaunchesRepository {

    override suspend fun getLaunches(year: String, reload: Boolean): List<LaunchData> {
        val priority = if (reload) LaunchesDataStoreFactory.Priority.CLOUD else LaunchesDataStoreFactory.Priority.CACHE
        val dataStore = launchesDataStoreFactory.create(year, priority)
        return launchDataMapper.map(dataStore.getLaunchEntityList(year))
    }

    override suspend fun getLaunchData(year: String, id: Int) = getLaunches(year)[id]
}