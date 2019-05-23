package com.github.johnnysc.spacex.domain.interactor.launch

import com.github.johnnysc.spacex.data.Year
import com.github.johnnysc.spacex.data.YearValidator
import com.github.johnnysc.spacex.data.network.ConnectionManager
import com.github.johnnysc.spacex.domain.LaunchesRepository

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesInteractorImpl(
    private val repository: LaunchesRepository,
    private val connectionManager: ConnectionManager,
    private val yearValidator: YearValidator
) : LaunchesInteractor {

    override fun isInputDataValid(year: Year): Boolean? =
        yearValidator.isValid(year)

    override suspend fun fetch(year: Year): Status =
        if (connectionManager.isNetworkAbsent()) {
            Status.NoConnection
        } else {
            val data = repository.fetch(year)
            when {
                data == null -> Status.ServiceUnavailable
                data.isEmpty() -> Status.NoResults
                else -> Status.Success
            }
        }
}