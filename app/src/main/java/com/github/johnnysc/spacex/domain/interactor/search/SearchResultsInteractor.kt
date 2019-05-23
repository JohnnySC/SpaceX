package com.github.johnnysc.spacex.domain.interactor.search

/**
 * @author Asatryan on 19.05.19
 */
interface SearchResultsInteractor {

    fun getResults(): List<String>
}