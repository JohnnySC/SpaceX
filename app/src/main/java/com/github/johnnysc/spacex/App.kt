package com.github.johnnysc.spacex

import android.app.Application
import com.github.johnnysc.spacex.di.DI

/**
 * @author Asatryan on 19.05.19
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DI.initialize(this)
    }
}