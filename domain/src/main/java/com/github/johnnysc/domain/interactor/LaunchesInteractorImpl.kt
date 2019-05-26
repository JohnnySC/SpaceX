package com.github.johnnysc.domain.interactor

import android.util.Log
import com.github.johnnysc.domain.exception.NetworkConnectionException
import com.github.johnnysc.domain.exception.ServerUnavailableException
import com.github.johnnysc.domain.validator.YearValidator
import com.github.johnnysc.domain.repository.LaunchesRepository
import java.lang.Exception

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesInteractorImpl(
    private val repository: LaunchesRepository,
    private val yearValidator: YearValidator
) : LaunchesInteractor {

    override fun isInputDataValid(year: String?): Boolean? {
        return yearValidator.isValid(year)
    }

    override suspend fun fetch(year: String): Status {
        return try {
            val list = repository.getLaunches(year)
            if (list.isEmpty())
                Status.NO_RESULTS
            else
                Status.SUCCESS
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("LaunchesInteractor", "exception $e")
            when (e) {
                is NetworkConnectionException -> Status.NO_CONNECTION
                is ServerUnavailableException -> Status.SERVICE_UNAVAILABLE
                else -> Status.UNKNOWN
            }
        }
    }
}