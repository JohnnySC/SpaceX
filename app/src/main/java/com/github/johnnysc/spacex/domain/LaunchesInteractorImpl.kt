package com.github.johnnysc.spacex.domain

import com.github.johnnysc.spacex.data.network.ConnectionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    override suspend fun fetch(year: String): Status =
        withContext(Dispatchers.Default) {
            if (year.length == YEAR_LENGTH)
                if (connectionManager.isNetworkAbsent()) {
                    Status.NO_CONNECTION
                } else {
                    val data = repository.fetch(year)
                    when {
                        data == null -> Status.SERVICE_UNAVAILABLE
                        data.isEmpty() -> Status.NO_RESULTS
                        else -> Status.SUCCESS
                    }
                }
            else
                Status.UNKNOWN
        }
}