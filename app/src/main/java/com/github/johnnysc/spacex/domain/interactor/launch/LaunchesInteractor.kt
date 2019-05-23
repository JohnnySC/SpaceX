package com.github.johnnysc.spacex.domain.interactor.launch

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesInteractor {

    fun isInputDataValid(year: String): Boolean?

    suspend fun fetch(year: String): Status
}

sealed class Status {
    object NoResults : Status()
    object ServiceUnavailable : Status()
    object NoConnection : Status()
    object Success : Status()
}