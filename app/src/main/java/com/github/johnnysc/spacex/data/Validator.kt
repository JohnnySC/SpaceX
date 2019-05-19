package com.github.johnnysc.spacex.data

/**
 * @author Asatryan on 20.05.19
 */
interface Validator<T> {

    fun isValid(source: T): Boolean?
}