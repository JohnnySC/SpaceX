package com.github.johnnysc.spacex.presentation.details.model

import com.github.johnnysc.domain.SecondStageData

/**
 * @author Asatryan on 06.06.19
 */
sealed class LaunchDataUiModel<T> {

    abstract val value: T

    data class FlightNumber(override val value: String) : LaunchDataUiModel<String>()
    data class MissionName(override val value: String) : LaunchDataUiModel<String>()
    data class LaunchYear(override val value: String) : LaunchDataUiModel<String>()
    data class LaunchDate(override val value: String) : LaunchDataUiModel<String>()
    data class Rocket(
        override val value: String,
        val type: String,
        val firstStageData: List<Pair<String, Boolean>>,
        val secondStage: SecondStageData
    ) : LaunchDataUiModel<String>()

    data class Ships(override val value: String) : LaunchDataUiModel<String>()
    data class LaunchPlace(override val value: String) : LaunchDataUiModel<String>()
    data class LaunchSuccess(override val value: Boolean) : LaunchDataUiModel<Boolean>()
    data class LinkTitle(val title: String, override val value: String) : LaunchDataUiModel<String>()
    data class Image(override val value: String) : LaunchDataUiModel<String>()
    data class PDF(val title: String, override val value: String) : LaunchDataUiModel<String>()
    data class Details(override val value: String) : LaunchDataUiModel<String>()
}