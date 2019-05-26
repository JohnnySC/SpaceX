package com.github.johnnysc.domain.interactor

import com.github.johnnysc.domain.LaunchData

/**
 * @author Asatryan on 26.05.19
 */
interface SearchResultsInteractor {

    suspend fun getSearchResults(year: String): List<LaunchData>
}