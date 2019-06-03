package com.github.johnnysc.spacex.di

import android.app.Application
import com.github.johnnysc.data.cache.LaunchesCache
import com.github.johnnysc.data.cache.LaunchesCacheImpl
import com.github.johnnysc.data.entity.mapper.LaunchDataMapper
import com.github.johnnysc.data.net.LaunchesService
import com.github.johnnysc.data.repository.LaunchesRepositoryImpl
import com.github.johnnysc.data.repository.datasource.CloudLaunchesDataStore
import com.github.johnnysc.data.repository.datasource.DiskLaunchesDataStore
import com.github.johnnysc.data.repository.datasource.LaunchesDataStoreFactoryImpl
import com.github.johnnysc.domain.interactor.*
import com.github.johnnysc.domain.repository.LaunchesRepository
import com.github.johnnysc.domain.validator.YearValidator
import retrofit2.Retrofit
import java.lang.UnsupportedOperationException

/**
 * @author Asatryan on 03.06.19
 */
object MainScreenModule {

    private lateinit var config: DI.Config
    private lateinit var repository: LaunchesRepository
    private lateinit var launchesCache: LaunchesCache

    private var launchDetailsInteractor: LaunchDetailsInteractor? = null
    private var launchesInteractor: LaunchesInteractor? = null
    private var searchResultsInteractor: SearchResultsInteractor? = null

    fun initialize(app: Application, configuration: DI.Config = DI.Config.RELEASE) {
        config = configuration
        launchesCache = LaunchesCacheImpl(app)
        repository = getLaunchesRepository()
    }

    fun getLaunchesInteractorImpl(): LaunchesInteractor {
        if (config == DI.Config.RELEASE && launchesInteractor == null)
            launchesInteractor = makeLaunchesInteractor(repository)
        return launchesInteractor!!
    }

    fun setLaunchesInteractor(interactor: LaunchesInteractor) =
        if (config == DI.Config.TEST) {
            launchesInteractor = interactor
        } else {
            throw UnsupportedOperationException("one cannot simply set interactor if not a DI.Config.TEST")
        }

    fun getSearchResultsInteractor(): SearchResultsInteractor {
        if (searchResultsInteractor == null)
            searchResultsInteractor = SearchResultsInteractorImpl(repository)
        return searchResultsInteractor!!
    }

    fun getLaunchDetailsInteractor(): LaunchDetailsInteractor {
        if (launchDetailsInteractor == null)
            launchDetailsInteractor = getLaunchDetailsInteractor(repository)
        return launchDetailsInteractor!!
    }

    //region private methods

    private fun getLaunchDetailsInteractor(repository: LaunchesRepository) =
        LaunchDetailsInteractorImpl(repository)

    private fun makeLaunchesInteractor(repository: LaunchesRepository) =
        LaunchesInteractorImpl(repository, YearValidator())

    private fun getLaunchesRepository() =
        LaunchesRepositoryImpl(getLaunchesDataStoreFactory(), LaunchDataMapper())

    private fun getDiskLaunchesDataStore() = DiskLaunchesDataStore(launchesCache)
    private fun getCloudLaunchesDataStore() = CloudLaunchesDataStore(
        NetworkModule.connectionManager,
        getLaunchService(NetworkModule.retrofit),
        launchesCache
    )

    private fun getLaunchService(retrofit: Retrofit) = retrofit.create(LaunchesService::class.java)

    private fun getLaunchesDataStoreFactory() =
        LaunchesDataStoreFactoryImpl(launchesCache, getDiskLaunchesDataStore(), getCloudLaunchesDataStore())

    //endregion
}