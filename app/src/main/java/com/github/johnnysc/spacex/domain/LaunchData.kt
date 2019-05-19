package com.github.johnnysc.spacex.domain

import com.github.johnnysc.spacex.data.RocketDTO

/**
 * @author Asatryan on 19.05.19
 */
data class LaunchData(
    val flightNumber: Int,
    val missionName: String,
    val launchYear: Int,
    val launchDate: String,
    val rocket: RocketDTO,
    val ships: List<String>,
    val telemetry: List<Link>,
    val reuse: Map<String, Boolean>,
    val launchPlace: String,
    val launchSuccess: Boolean,
    val links: List<Link>,
    val images: List<ImageLink>,
    val PDFs: List<PDFLink>,
    val details: String,
    val upcoming: Boolean,
    val staticFireDate: String,
    val timeline: Map<String, Int>
)

data class Link(val address: String)

data class ImageLink(val address: String)

data class PDFLink(val address: String)