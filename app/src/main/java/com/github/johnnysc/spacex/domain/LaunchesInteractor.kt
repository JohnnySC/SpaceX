package com.github.johnnysc.spacex.domain

/**
 * @author Asatryan on 18.05.19
 */
interface LaunchesInteractor {

    suspend fun fetch(year: String?): Status
}

enum class Status {
    NO_RESULTS,
    SERVICE_UNAVAILABLE,
    NO_CONNECTION,
    SUCCESS,
    UNKNOWN
}