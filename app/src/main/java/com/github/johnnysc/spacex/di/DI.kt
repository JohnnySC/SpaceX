package com.github.johnnysc.spacex.di

import com.github.johnnysc.spacex.App
import com.github.johnnysc.spacex.data.LaunchDataMapper
import com.github.johnnysc.spacex.data.LaunchesRepositoryImpl
import com.github.johnnysc.spacex.data.SearchResultsMapper
import com.github.johnnysc.spacex.data.YearValidator
import com.github.johnnysc.spacex.data.cache.CacheManager
import com.github.johnnysc.spacex.data.cache.CacheManagerImpl
import com.github.johnnysc.spacex.data.network.ConnectionManager
import com.github.johnnysc.spacex.data.network.ConnectionManagerImpl
import com.github.johnnysc.spacex.data.network.LaunchesService
import com.github.johnnysc.spacex.domain.LaunchesRepository
import com.github.johnnysc.spacex.domain.interactor.launch.LaunchesInteractor
import com.github.johnnysc.spacex.domain.interactor.launch.LaunchesInteractorImpl
import com.github.johnnysc.spacex.domain.interactor.launch.details.LaunchDetailsInteractor
import com.github.johnnysc.spacex.domain.interactor.launch.details.LaunchDetailsInteractorImpl
import com.github.johnnysc.spacex.domain.interactor.search.SearchResultsInteractor
import com.github.johnnysc.spacex.domain.interactor.search.SearchResultsInteractorImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.picasso.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Asatryan on 19.05.19
 */
class DI(private val application: App) {

    private val retrofit: Retrofit =
        createRetrofit(createOkHttpClient(createInterceptor()))

    private val repository: LaunchesRepository =
        createLaunchesRepository(createLaunchService(retrofit), createCacheManager())

    private val connectionManager: ConnectionManager =
        createConnectionManager()

    private var launchesInteractor: LaunchesInteractor? = null
    private var searchResultsInteractor: SearchResultsInteractor? = null
    private var launchDetailsInteractor: LaunchDetailsInteractor? = null

    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v2/"
    }

    //todo create actualizeDataInteractor, get the current year's data and clear it on workManager when charging and idle

    fun getLaunchesInteractor(): LaunchesInteractor =
        launchesInteractor
            ?: createLaunchesInteractor(repository, connectionManager).also {
                launchesInteractor = it
            }

    fun getSearchResultsInteractor(): SearchResultsInteractor =
        searchResultsInteractor
            ?: createSearchResultsInteractor(repository, SearchResultsMapper()).also {
                searchResultsInteractor = it
            }

    fun getLaunchDetailsInteractor(): LaunchDetailsInteractor =
        launchDetailsInteractor
            ?: createLaunchDetailsInteractor(repository, LaunchDataMapper()).also {
                launchDetailsInteractor = it
            }

    //region private methods

    private fun createConnectionManager() =
        ConnectionManagerImpl(application.applicationContext)

    private fun createInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    private fun createRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    private fun createLaunchService(retrofit: Retrofit) =
        retrofit.create(LaunchesService::class.java)

    private fun createCacheManager() =
        CacheManagerImpl(application.applicationContext)

    private fun createLaunchesRepository(service: LaunchesService, cacheManager: CacheManager) =
        LaunchesRepositoryImpl(service, cacheManager)

    private fun createLaunchesInteractor(repository: LaunchesRepository, connectionManager: ConnectionManager) =
        LaunchesInteractorImpl(
            repository,
            connectionManager,
            YearValidator()
        )

    private fun createSearchResultsInteractor(
        repository: LaunchesRepository,
        searchResultsMapper: SearchResultsMapper
    ) =
        SearchResultsInteractorImpl(repository, searchResultsMapper)

    private fun createLaunchDetailsInteractor(repository: LaunchesRepository, dataMapper: LaunchDataMapper) =
        LaunchDetailsInteractorImpl(repository, dataMapper)

    //endregion
}