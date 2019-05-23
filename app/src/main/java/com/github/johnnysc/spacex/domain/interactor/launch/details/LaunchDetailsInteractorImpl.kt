package com.github.johnnysc.spacex.domain.interactor.launch.details

import com.github.johnnysc.spacex.data.LaunchDataMapper
import com.github.johnnysc.spacex.def
import com.github.johnnysc.spacex.domain.LaunchesRepository
import com.github.johnnysc.spacex.domain.entity.LaunchData

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsInteractorImpl(
    private val repository: LaunchesRepository,
    private val launchDataMapper: LaunchDataMapper
) : LaunchDetailsInteractor {

    override suspend fun getLaunchData(position: Int): LaunchData = def {
        launchDataMapper.map(repository.getLaunches()[position])
    }
}