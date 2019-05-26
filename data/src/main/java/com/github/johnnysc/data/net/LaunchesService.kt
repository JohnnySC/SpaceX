package com.github.johnnysc.data.net

import com.github.johnnysc.data.entity.LaunchesEntity
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesService {

    @GET("launches")
    fun getLaunchesAsync(@Query("launch_year") year: String) : Deferred<Response<List<LaunchesEntity>>>
}