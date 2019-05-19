package com.github.johnnysc.spacex.data

import com.google.gson.annotations.SerializedName

/**
 * @author Asatryan on 18.05.19
 */
data class LaunchesDTO(
    @SerializedName("flight_number") val flightNumber: Int,
    @SerializedName("mission_name") val missionName: String?
)