package com.github.johnnysc.spacex.data

/**
 * @author Asatryan on 19.05.19
 */
interface Mapper<S, R> {

    fun map(source: S): R
}