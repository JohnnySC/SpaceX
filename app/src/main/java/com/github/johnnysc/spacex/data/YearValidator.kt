package com.github.johnnysc.spacex.data

import com.github.johnnysc.spacex.currentYear

/**
 * @author Asatryan on 20.05.19
 */
class YearValidator : Validator<String> {

    companion object {
        const val MINIMUM_YEAR = 1
        const val YEAR_LENGTH = 4
    }

    override fun isValid(source: String): Boolean? =
        if (source.isEmpty() || source.length < YEAR_LENGTH)
            null
        else
            source.toIntOrNull() in MINIMUM_YEAR..currentYear
}