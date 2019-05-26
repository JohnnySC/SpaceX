package com.github.johnnysc.domain.interactor

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesInteractor {

    fun isInputDataValid(year: String?): Boolean?

    suspend fun fetch(year: String): Status
}

enum class Status {
    NO_RESULTS,
    SERVICE_UNAVAILABLE,
    NO_CONNECTION,
    SUCCESS,
    UNKNOWN
}