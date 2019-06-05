package com.github.johnnysc.data.cache

import android.content.Context
import com.github.johnnysc.data.entity.LaunchesEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Asatryan on 19.05.19
 */
class LaunchesCacheImpl(context: Context) : LaunchesCache {

    private val sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun put(year: String, launches: List<LaunchesEntity>) {
        val json = gson.toJson(launches)
        sharedPreferences.edit().putString(year, json).apply()
    }

    override fun get(year: String): List<LaunchesEntity> {
        val json = sharedPreferences.getString(year, "")
        val type = object : TypeToken<List<LaunchesEntity>>() {
        }.type
        return Gson().fromJson(json, type)
    }

    override fun isCached(year: String): Boolean {
        val value = sharedPreferences.getString(year, null)
        return value?.isNotEmpty() ?: false
    }
}