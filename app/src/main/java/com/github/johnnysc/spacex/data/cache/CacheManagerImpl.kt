package com.github.johnnysc.spacex.data.cache

import android.content.Context
import com.github.johnnysc.spacex.data.LaunchesDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Asatryan on 19.05.19
 */
class CacheManagerImpl(context: Context) : CacheManager {

    private val sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE)

    override fun saveLaunches(launches: Map<String, List<LaunchesDTO>>) {
        val string = Gson().toJson(launches)
        sharedPreferences
            .edit()
            .putString(CacheManager.LAUNCHES, string)
            .apply()
    }

    override fun getLaunches(): MutableMap<String, List<LaunchesDTO>> {
        val string = sharedPreferences.getString(CacheManager.LAUNCHES, "")
        if (string.isNullOrEmpty())
            return HashMap()
        val type = object : TypeToken<HashMap<String, List<LaunchesDTO>>>() {}.type
        return Gson().fromJson(string, type)
    }

    override fun saveLastQuery(query: String) =
        sharedPreferences.edit().putString(CacheManager.LAST_QUERY, query).apply()

    override fun getLastQuery(): String =
        sharedPreferences.getString(CacheManager.LAST_QUERY, null) ?: ""
}