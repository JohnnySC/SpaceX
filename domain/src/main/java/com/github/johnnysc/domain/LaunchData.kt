package com.github.johnnysc.domain

/**
 * @author Asatryan on 19.05.19
 */
data class LaunchData(
    val flightNumber: Int,
    val missionName: String,
    val launchYear: Int,
    val launchDate: String?,
    val rocket: RocketData,
    val ships: List<String>,
    val telemetry: List<Link>,
    val reuse: Map<String, Boolean>,
    val launchPlace: String,
    val launchSuccess: Boolean,
    val links: List<Link>,
    val images: List<ImageLink>,
    val PDFs: List<PDFLink>,
    val details: String?,
    val upcoming: Boolean,
    val staticFireDate: String?,
    val timeline: Map<String, Int>?
)

data class Link(val address: String)

data class ImageLink(val address: String)

data class PDFLink(val address: String)

data class RocketData(
    val name: String,
    val type: String,
    val firstStage: FirstStageData,
    val secondStage: SecondStageData
)

data class FirstStageData(val cores: List<CoreData>)

data class SecondStageData(
    val block: Int,
    val payloads: List<PayloadData>
)

data class CoreData(
    val coreSerial: String,
    val reused: Boolean
)

data class PayloadData(
    val manufacturer: String?,
    val nationality: String?,
    val payloadType: String?,
    val payloadMassKg: Double?,
    val orbit: String?
)