package com.github.johnnysc.domain.interactor

import com.github.johnnysc.domain.repository.LaunchesRepository

/**
 * @author Asatryan on 26.05.19
 */
class SearchResultsInteractorImpl(
    private val launchesRepository: LaunchesRepository
) : SearchResultsInteractor {

    override suspend fun getSearchResults(year: String) = launchesRepository.getLaunches(year)
}