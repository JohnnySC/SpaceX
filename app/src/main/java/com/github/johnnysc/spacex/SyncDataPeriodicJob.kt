package com.github.johnnysc.spacex

import android.content.Context
import androidx.work.*
import com.github.johnnysc.spacex.di.MainScreenModule
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Asatryan on 04.06.19
 */
class SyncDataPeriodicJob {

    companion object {
        private const val syncDataPeriodicJobId = "updateCurrentYearData"
    }

    fun start() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
        val work = PeriodicWorkRequest.Builder(SyncDataPeriodicWorker::class.java, 2, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(syncDataPeriodicJobId, ExistingPeriodicWorkPolicy.REPLACE, work)
    }
}

private class SyncDataPeriodicWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        MainScreenModule.getLaunchesInteractorImpl().fetch(currentYear)
        return Result.success()
    }
}