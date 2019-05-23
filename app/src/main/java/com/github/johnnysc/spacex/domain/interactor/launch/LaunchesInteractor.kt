package com.github.johnnysc.spacex.domain.interactor.launch

import com.github.johnnysc.spacex.data.Year

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesInteractor {

    fun isInputDataValid(year: Year): Boolean?

    suspend fun fetch(year: Year): Status
}

sealed class Status {
    object NoResults : Status()
    object ServiceUnavailable : Status()
    object NoConnection : Status()
    object Success : Status()
}