package com.github.johnnysc.spacex.domain

import com.github.johnnysc.spacex.data.network.ConnectionManager

/**
 * @author Asatryan on 18.05.19
 */
class LaunchesInteractorImpl(
    private val repository: LaunchesRepository,
    private val connectionManager: ConnectionManager
) : LaunchesInteractor {

    companion object {
        const val YEAR_LENGTH = 4
    }

    override suspend fun fetch(year: String?): Status {
        if (year?.length == YEAR_LENGTH) {
            if (connectionManager.isNetworkAbsent()) {
                return Status.NO_CONNECTION
            }
            val data = repository.fetch(year)
            return when {
                data == null -> Status.SERVICE_UNAVAILABLE
                data.isEmpty() -> Status.NO_RESULTS
                else -> Status.SUCCESS
            }
        } else {
            return Status.UNKNOWN
        }
    }
}