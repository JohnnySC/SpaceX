package com.github.johnnysc.spacex.di

import android.content.Context
import android.net.ConnectivityManager
import com.github.johnnysc.data.cache.LaunchesCache
import com.github.johnnysc.data.cache.LaunchesCacheImpl
import com.github.johnnysc.data.entity.mapper.LaunchDataMapper
import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.BuildConfig
import com.github.johnnysc.domain.validator.YearValidator
import com.github.johnnysc.data.net.ConnectionManager
import com.github.johnnysc.data.net.ConnectionManagerImpl
import com.github.johnnysc.data.net.LaunchesService
import com.github.johnnysc.data.repository.LaunchesRepositoryImpl
import com.github.johnnysc.data.repository.datasource.CloudLaunchesDataStore
import com.github.johnnysc.data.repository.datasource.DiskLaunchesDataStore
import com.github.johnnysc.data.repository.datasource.LaunchesDataStoreFactoryImpl
import com.github.johnnysc.domain.interactor.*
import com.github.johnnysc.domain.repository.LaunchesRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Asatryan on 19.05.19
 */
class DI(application: App) {

    private var retrofit: Retrofit
    private var repository: LaunchesRepository
    private var connectionManager: ConnectionManager
    private var launchesCache: LaunchesCache
    private var launchesInteractor: LaunchesInteractor? = null
    private var launchDetailsInteractor: LaunchDetailsInteractor? = null

    companion object {
        private const val BASE_URL = "https://api.spacexdata.com/v2/"
    }

    init {
        launchesCache = LaunchesCacheImpl(application.applicationContext)
        connectionManager = ConnectionManagerImpl(getConnectivityManager(application.applicationContext))
        retrofit = getRetrofit(getOkHttpClient(getInterceptor()))
        repository = getLaunchesRepository()
    }

    //todo create actualizeDataInteractor, get the current year's data and clear it on workManager when charging and idle

    fun getSearchResultsInteractor() = SearchResultsInteractorImpl(repository)

    fun getLaunchesInteractor(): LaunchesInteractor {
        if (launchesInteractor == null) {
            launchesInteractor = makeLaunchesInteractor(repository)
        }
        return launchesInteractor!!
    }

    fun getLaunchDetailsInteractor(): LaunchDetailsInteractor {
        if (launchDetailsInteractor == null) {
            launchDetailsInteractor = getLaunchDetailsInteractor(repository)
        }
        return launchDetailsInteractor!!
    }

    //region private methods

    private fun getConnectivityManager(context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    private fun getConnectionManager() = connectionManager

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

    private fun getLaunchesCache() = launchesCache

    private fun getLaunchesRepository() =
        LaunchesRepositoryImpl(getLaunchesDataStoreFactory(), LaunchDataMapper())

    private fun getDiskLaunchesDataStore() = DiskLaunchesDataStore(getLaunchesCache())
    private fun getCloudLaunchesDataStore() =
        CloudLaunchesDataStore(getConnectionManager(), getLaunchService(retrofit), getLaunchesCache())

    private fun getLaunchesDataStoreFactory() =
        LaunchesDataStoreFactoryImpl(getLaunchesCache(), getDiskLaunchesDataStore(), getCloudLaunchesDataStore())

    private fun makeLaunchesInteractor(repository: LaunchesRepository) =
        LaunchesInteractorImpl(repository, YearValidator())

    private fun getLaunchDetailsInteractor(repository: LaunchesRepository) =
        LaunchDetailsInteractorImpl(repository)

    //endregion
}