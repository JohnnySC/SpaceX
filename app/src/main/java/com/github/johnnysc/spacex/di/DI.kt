package com.github.johnnysc.spacex.di

import android.app.Application
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.BuildConfig
import com.github.johnnysc.spacex.data.LaunchesRepositoryImpl
import com.github.johnnysc.spacex.data.cache.CacheManager
import com.github.johnnysc.spacex.data.cache.CacheManagerImpl
import com.github.johnnysc.spacex.data.network.ConnectionManager
import com.github.johnnysc.spacex.data.network.ConnectionManagerImpl
import com.github.johnnysc.spacex.data.network.LaunchesService
import com.github.johnnysc.spacex.domain.LaunchesInteractor
import com.github.johnnysc.spacex.domain.LaunchesInteractorImpl
import com.github.johnnysc.spacex.domain.LaunchesRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Asatryan on 19.05.19
 */
object DI {
    private var application: App? = null
    private lateinit var retrofit: Retrofit
    private lateinit var repository: LaunchesRepository
    private lateinit var connectionManager: ConnectionManager

    const val BASE_URL = "https://api.spacexdata.com/v2/"

    fun initialize(application: App) {
        this.application = application
        retrofit = getRetrofit(getOkHttpClient(getInterceptor()))
        repository = getLaunchesRepository(getLaunchService(retrofit), getCacheManager())
        connectionManager = getConnectionManager()
    }

    fun getLaunchesInteractor(): LaunchesInteractor =
        getLaunchesInteractor(repository, connectionManager)

    private fun getConnectionManager() = ConnectionManagerImpl(application!!.applicationContext)

    private fun getInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    private fun getOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }

    private fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun getLaunchService(retrofit: Retrofit) = retrofit.create(LaunchesService::class.java)

    private fun getCacheManager() = CacheManagerImpl(application!!.applicationContext)

    private fun getLaunchesRepository(service: LaunchesService, cacheManager: CacheManager) =
        LaunchesRepositoryImpl(service, cacheManager)

    private fun getLaunchesInteractor(repository: LaunchesRepository, connectionManager: ConnectionManager) =
        LaunchesInteractorImpl(repository, connectionManager)
}