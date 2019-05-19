package com.github.johnnysc.spacex.domain

import com.github.johnnysc.spacex.data.SearchResultsMapper

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsInteractorImpl(
    private val repository: LaunchesRepository,
    private val launchNameMapper: SearchResultsMapper
) : SearchResultsInteractor {

    override fun getResults() = launchNameMapper.map(repository.getLaunchesInCache())
}