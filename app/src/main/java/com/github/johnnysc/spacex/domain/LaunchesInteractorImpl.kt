package com.github.johnnysc.spacex.domain

import com.github.johnnysc.spacex.data.YearValidator
import com.github.johnnysc.spacex.data.network.ConnectionManager

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesInteractorImpl(
    private val repository: LaunchesRepository,
    private val connectionManager: ConnectionManager,
    private val yearValidator: YearValidator
) : LaunchesInteractor {


    override fun isInputDataValid(year: String?): Boolean? {
        return yearValidator.isValid(year)
    }

    override suspend fun fetch(year: String): Status {
        if (connectionManager.isNetworkAbsent()) {
            return Status.NO_CONNECTION
        }
        val data = repository.fetch(year)
        return when {
            data == null -> Status.SERVICE_UNAVAILABLE
            data.isEmpty() -> Status.NO_RESULTS
            else -> Status.SUCCESS
        }
    }
}