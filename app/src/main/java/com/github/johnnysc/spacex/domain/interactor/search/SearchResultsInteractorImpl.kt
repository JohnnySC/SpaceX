package com.github.johnnysc.spacex.domain.interactor.search

import com.github.johnnysc.spacex.data.SearchResultsMapper
import com.github.johnnysc.spacex.def
import com.github.johnnysc.spacex.domain.LaunchesRepository

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsInteractorImpl(
    private val repository: LaunchesRepository,
    private val launchNameMapper: SearchResultsMapper
) : SearchResultsInteractor {

    override suspend fun getResults(): List<String> = def {
        launchNameMapper.map(repository.getLaunches())
    }
}