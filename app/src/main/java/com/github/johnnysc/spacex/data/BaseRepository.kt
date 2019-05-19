package com.github.johnnysc.spacex.data

import android.util.Log
import retrofit2.Response
import java.io.IOException

/**
 * @author Asatryan on 18.05.19
 */
open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? =
        safeApiResult(call, errorMessage)
            .onFailure { Log.d("BaseRepository", "$errorMessage & Exception - $it") }
            .getOrNull()


    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String): Result<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            Result.success(response.body()!!)
        else
            Result.failure(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}