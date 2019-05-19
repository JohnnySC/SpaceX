package com.github.johnnysc.spacex.data

import com.github.johnnysc.spacex.domain.ImageLink
import com.github.johnnysc.spacex.domain.LaunchData
import com.github.johnnysc.spacex.domain.Link
import com.github.johnnysc.spacex.domain.PDFLink
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

    override fun map(source: LaunchesDTO): LaunchData {
        return LaunchData(
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
    }

    private fun getPDFs(data: Map<String, Any>): List<PDFLink> {
        val list = ArrayList<PDFLink>()
        data.forEach { (_, value) ->
            if (value is String && value.endsWith(".pdf"))
                list.add(PDFLink(value))
        }
        return list
    }

    private fun getPureLinks(data: Map<String, Any>): List<Link> {
        val list = ArrayList<Link>(1)
        data.forEach { (key, value) ->
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
        val list = ArrayList<ImageLink>()
        data.forEach { (_, value) ->
            if (value is String) {
                if (value.endsWith(".png") ||
                    value.endsWith(".jpg") ||
                    value.endsWith(".jpeg")
                )
                    list.add(ImageLink(value))

            } else if (value is List<*>) {
                value.forEach {
                    if (it is String && (it.endsWith(".png") ||
                                it.endsWith(".jpg") ||
                                it.endsWith(".jpeg"))
                    )
                        list.add(ImageLink(it))
                }
            }
        }
        return list
    }

    private fun makeLinks(data: Map<String, String>): List<Link> {
        val list = ArrayList<Link>(data.size)
        data.forEach { (_, v) -> list.add(Link(v)) }
        return list
    }

    private fun getDate(source: String): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy' at 'HH:mm", Locale.US)
        val date = ISO8601Utils.parse(source, ParsePosition(0))
        return dateFormat.format(date)
    }
}
