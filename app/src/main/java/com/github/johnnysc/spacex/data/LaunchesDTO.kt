package com.github.johnnysc.spacex.data

import com.google.gson.annotations.SerializedName

/**
 * @author Asatryan on 18.05.19
 */
data class LaunchesDTO(
    @SerializedName("flight_number") private val flightNumber: Int,
    @SerializedName("mission_name") private val missionName: String?
)