package com.github.johnnysc.spacex.data.network

import com.github.johnnysc.spacex.data.LaunchesDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesService {

    @GET("launches")
    fun getLaunchesAsync(@Query("launch_year") year: String) : Deferred<Response<List<LaunchesDTO>>>
}