package com.github.johnnysc.domain.interactor

import com.github.johnnysc.domain.exception.NetworkConnectionException
import com.github.johnnysc.domain.validator.YearValidator
import com.github.johnnysc.domain.repository.LaunchesRepository
import java.lang.Exception
import java.util.*

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesInteractorImpl(
    private val repository: LaunchesRepository,
    private val yearValidator: YearValidator
) : LaunchesInteractor {

    override fun isInputDataValid(year: String?) = yearValidator.isValid(year)

    override suspend fun fetch(year: String): Status {
        val reload = Calendar.getInstance().get(Calendar.YEAR).toString() == year
        return getData(year, reload, 3)
    }

    private suspend fun getData(year: String, reload: Boolean, retryCount: Int): Status =
        try {
            val list = repository.getLaunches(year, reload)
            if (list.isEmpty())
                Status.NO_RESULTS
            else
                Status.SUCCESS
        } catch (e: Exception) {
            e.printStackTrace()
            if (retryCount > 0) {
                getData(year, retryCount > 1, retryCount - 1)
            } else {
                if (e is NetworkConnectionException)
                    Status.NO_CONNECTION
                else
                    Status.SERVICE_UNAVAILABLE
            }
        }
}