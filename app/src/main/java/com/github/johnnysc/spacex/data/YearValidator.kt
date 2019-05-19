package com.github.johnnysc.spacex.data

import java.util.*

/**
 * @author Asatryan on 20.05.19
 */
class YearValidator : Validator<String?> {

    companion object {
        const val MINIMUM_YEAR = 0
        const val YEAR_LENGTH = 4
    }

    override fun isValid(source: String?): Boolean? {
        return if (source.isNullOrEmpty() || source.length < YEAR_LENGTH) {
            null
        } else {
            try {
                val year = source.toInt()
                year > MINIMUM_YEAR && year <= Calendar.getInstance().get(Calendar.YEAR)
            } catch (e: NumberFormatException) {
                false
            }
        }
    }
}