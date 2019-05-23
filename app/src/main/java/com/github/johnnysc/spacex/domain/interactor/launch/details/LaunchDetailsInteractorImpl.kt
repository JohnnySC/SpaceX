package com.github.johnnysc.spacex.domain.interactor.launch.details

import com.github.johnnysc.spacex.data.LaunchDataMapper
import com.github.johnnysc.spacex.domain.LaunchesRepository

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDetailsInteractorImpl(
    private val repository: LaunchesRepository,
    private val launchDataMapper: LaunchDataMapper
) : LaunchDetailsInteractor {

    override fun getLaunchData(position: Int) =
        launchDataMapper.map(repository.getLaunchesInCache()[position])
}