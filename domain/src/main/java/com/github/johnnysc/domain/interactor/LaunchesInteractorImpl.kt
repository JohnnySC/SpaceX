package com.github.johnnysc.domain.interactor

import com.github.johnnysc.domain.exception.NetworkConnectionException
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

    override fun isInputDataValid(year: String?) = yearValidator.isValid(year)

    override suspend fun fetch(year: String) =
        try {
            val list = repository.getLaunches(year)
            if (list.isEmpty())
                Status.NO_RESULTS
            else
                Status.SUCCESS
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is NetworkConnectionException)
                Status.NO_CONNECTION
            else
                Status.SERVICE_UNAVAILABLE
        }
}