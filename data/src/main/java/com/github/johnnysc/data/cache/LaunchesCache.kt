package com.github.johnnysc.data.cache

import com.github.johnnysc.data.entity.LaunchesEntity

/**
 * @author Asatryan on 19.05.19
 */
interface LaunchesCache {

    fun put(year: String, launches: List<LaunchesEntity>)

    fun get(year: String): List<LaunchesEntity>

    fun isCached(year: String): Boolean

    fun evict(year: String)
}