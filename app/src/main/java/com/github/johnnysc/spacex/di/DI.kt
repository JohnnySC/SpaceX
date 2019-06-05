package com.github.johnnysc.spacex.di

import android.app.Application
import com.github.johnnysc.spacex.SyncDataPeriodicJob

/**
 * @author Asatryan on 19.05.19
 */
object DI {

    sealed class Config {
        object RELEASE : Config()
        object TEST : Config()
    }

    fun initialize(app: Application, configuration: Config = DI.Config.RELEASE) {
        NetworkModule.initialize(app)
        MainScreenModule.initialize(app, configuration)
        SyncDataPeriodicJob().start()
    }
}