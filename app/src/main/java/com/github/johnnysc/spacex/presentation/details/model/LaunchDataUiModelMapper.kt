package com.github.johnnysc.spacex.presentation.details.model

import com.github.johnnysc.data.entity.mapper.Mapper
import com.github.johnnysc.domain.LaunchData

/**
 * @author Asatryan on 06.06.19
 */
class LaunchDataUiModelMapper : Mapper<LaunchData, List<LaunchDataUiModel<*>>> {

    override fun map(source: LaunchData) =
        ArrayList<LaunchDataUiModel<*>>().apply {
            add(LaunchDataUiModel.MissionName(source.missionName))
            add(LaunchDataUiModel.FlightNumber(source.flightNumber.toString()))
            add(LaunchDataUiModel.LaunchYear(source.launchYear.toString()))
            if (!source.launchDate.isNullOrEmpty())
                add(LaunchDataUiModel.LaunchDate(source.launchDate!!))

            if (!source.details.isNullOrEmpty())
                add(LaunchDataUiModel.Details(source.details!!))

            val cores = source.rocket.firstStage.cores
            if (cores.isNotEmpty()) {
                val firstStageData = ArrayList<Pair<String, Boolean>>().apply {
                    for (core in cores) {
                        add(Pair(core.coreSerial, core.reused))
                    }
                }
                add(
                    LaunchDataUiModel.Rocket(
                        source.rocket.name,
                        source.rocket.type,
                        firstStageData,
                        source.rocket.secondStage
                    )
                )
            }

            if (source.ships.isNotEmpty()) {
                var ships = ""
                for (ship in source.ships) ships += ship + "\n"
                add(LaunchDataUiModel.Ships(ships))
            }

            add(LaunchDataUiModel.LaunchPlace(source.launchPlace))
            add(LaunchDataUiModel.LaunchSuccess(source.launchSuccess))

            source.links.forEach {
                add(
                    LaunchDataUiModel.LinkTitle(
                        it.title,
                        it.address
                    )
                )
            }

            source.images.forEach {
                add(LaunchDataUiModel.Image(it.address))
            }

            source.PDFs.forEach {
                add(LaunchDataUiModel.PDF(it.title, it.address))
            }

        }
}