package com.github.johnnysc.spacex.data.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.johnnysc.spacex.data.LaunchesDTO
import com.github.johnnysc.spacex.data.Year
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Asatryan on 19.05.19
 */
class CacheManagerImpl(context: Context) : CacheManager {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("cache", Context.MODE_PRIVATE)

    override fun saveLaunchesForYears(launches: Map<Year, List<LaunchesDTO>>) {
        val string = Gson().toJson(launches)
        sharedPreferences.edit { putString(CacheManager.LAUNCHES, string) }
    }

    override fun getLaunchesForYears(): Map<Year, List<LaunchesDTO>> {
        val string = sharedPreferences.getString(CacheManager.LAUNCHES, "")
        if (string.isNullOrEmpty())
            return HashMap()

        val type = object : TypeToken<HashMap<Year, List<LaunchesDTO>>>() {}.type
        return Gson().fromJson(string, type)
    }

    override fun saveLastQuery(query: String) =
        sharedPreferences.edit { putString(CacheManager.LAST_QUERY, query) }

    override fun getLastQuery(): String =
        sharedPreferences.getString(CacheManager.LAST_QUERY, "") ?: ""
}