package com.github.johnnysc.spacex.data

/**
 * @author Asatryan on 19.05.19
 */
class SearchResultsMapper : Mapper<List<LaunchesDTO>, List<String>> {

    override fun map(source: List<LaunchesDTO>): List<String> =
        source.map { it.missionName }
}