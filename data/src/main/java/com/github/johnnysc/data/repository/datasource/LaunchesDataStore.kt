package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.entity.LaunchesEntity

/**
 * @author Asatryan on 26.05.19
 */
interface LaunchesDataStore {

    suspend fun getLaunchEntityList(year: String): List<LaunchesEntity>

    suspend fun getLaunchDetails(year: String, id: Int): LaunchesEntity
}