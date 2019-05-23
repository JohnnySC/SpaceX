package com.github.johnnysc.spacex

import android.app.Application
import com.github.johnnysc.spacex.di.interactorModule
import com.github.johnnysc.spacex.di.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

/**
 * @author Asatryan on 19.05.19
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            logger(EmptyLogger())
            modules(
                interactorModule,
                repoModule
            )
        }
    }
}