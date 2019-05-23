package com.github.johnnysc.spacex.data.repo

import android.util.Log
import retrofit2.Response
import java.io.IOException

/**
 * @author Asatryan on 18.05.19
 */
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        errorMessage: String,
        call: suspend () -> Response<T>
    ): T? =
        safeApiResult(errorMessage, call)
            .onFailure { Log.d("BaseRepository", "$errorMessage & Exception - $it") }
            .getOrNull()

    private suspend inline fun <T : Any> safeApiResult(
        errorMessage: String,
        crossinline call: suspend () -> Response<T>
    ): Result<T> {
        val responseBody = call.invoke().body()
        return if (responseBody != null)
            Result.success(responseBody)
        else
            Result.failure(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}