package com.github.johnnysc.data.entity.mapper

/**
 * @author Asatryan on 19.05.19
 */
interface Mapper<S, R> {

    fun map(source: S): R
}