package com.github.johnnysc.spacex.domain.interactor.search

import com.github.johnnysc.spacex.data.SearchResultsMapper
import com.github.johnnysc.spacex.domain.LaunchesRepository

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsInteractorImpl(
    private val repository: LaunchesRepository,
    private val launchNameMapper: SearchResultsMapper
) : SearchResultsInteractor {

    override fun getResults(): List<String> =
        launchNameMapper.map(repository.getLaunchesInCache())
}