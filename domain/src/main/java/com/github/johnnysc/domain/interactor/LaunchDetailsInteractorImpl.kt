package com.github.johnnysc.domain.interactor

import com.github.johnnysc.domain.repository.LaunchesRepository

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsInteractorImpl(
    private val repository: LaunchesRepository
) : LaunchDetailsInteractor {

    override suspend fun getLaunchData(year: String, position: Int) =
        repository.getLaunchData(year, position)
}