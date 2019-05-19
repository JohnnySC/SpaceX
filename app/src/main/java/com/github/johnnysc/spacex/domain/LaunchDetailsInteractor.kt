package com.github.johnnysc.spacex.domain

/**
 * @author Asatryan on 19.05.19
 */
interface LaunchDetailsInteractor {

    fun getLaunchData(position: Int) : LaunchData
}