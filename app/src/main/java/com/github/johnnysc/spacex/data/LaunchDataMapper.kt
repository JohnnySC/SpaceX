package com.github.johnnysc.spacex.data

import com.github.johnnysc.spacex.domain.entity.ImageLink
import com.github.johnnysc.spacex.domain.entity.LaunchData
import com.github.johnnysc.spacex.domain.entity.Link
import com.github.johnnysc.spacex.domain.entity.PDFLink
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Asatryan on 19.05.19
 */
class LaunchDataMapper : Mapper<LaunchesDTO, LaunchData> {

    override fun map(source: LaunchesDTO): LaunchData =
        LaunchData(
            source.flightNumber,
            source.missionName,
            source.launchYear,
            getDate(source.launchDateUTC),
            source.rocket,
            source.ships,
            makeLinks(source.telemetry),
            source.reuse,
            source.launchSite.longName,
            source.launchSuccess,
            getPureLinks(source.links),
            getImages(source.links),
            getPDFs(source.links),
            source.details,
            source.upcoming,
            getDate(source.staticFireDateUTC),
            source.timeline
        )

    private fun getPDFs(data: Map<String, Any>): List<PDFLink> {
        val list = ArrayList<PDFLink>()
        for ((_, value) in data) {
            if (value is String && value.endsWith(".pdf"))
                list.add(PDFLink(value))
        }
        return list
    }

    private fun getPureLinks(data: Map<String, Any>): List<Link> {
        val list = ArrayList<Link>(1)
        for ((key, value) in data) {
            if (value is String && key != "youtube_id") {
                if (!value.endsWith(".png") &&
                    !value.endsWith(".pdf") &&
                    !value.endsWith(".jpg") &&
                    !value.endsWith(".jpeg")
                )
                    list.add(Link(value))
            }
        }
        return list
    }

    private fun getImages(data: Map<String, Any>): List<ImageLink> {
        val result = ArrayList<ImageLink>()
        for ((_, value) in data) {
            if (value is String) {
                if (value.endsWith(".png") ||
                    value.endsWith(".jpg") ||
                    value.endsWith(".jpeg")
                )
                    result.add(ImageLink(value))

            } else if (value is List<*>) {
                for (address in value) {
                    if (address is String && (address.endsWith(".png") ||
                                address.endsWith(".jpg") ||
                                address.endsWith(".jpeg"))
                    )
                        result.add(ImageLink(address))
                }
            }
        }
        return result
    }

    private fun makeLinks(data: Map<String, String>): List<Link> =
        data.map { Link(it.value) }

    private fun getDate(source: String): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy' at 'HH:mm", Locale.US)
        val date = ISO8601Utils.parse(source, ParsePosition(0))
        return dateFormat.format(date)
    }
}
