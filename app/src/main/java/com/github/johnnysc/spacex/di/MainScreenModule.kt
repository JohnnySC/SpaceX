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
import java.lang.UnsupportedOperationException

/**
 * @author Asatryan on 03.06.19
 */
object MainScreenModule {

    private lateinit var config: DI.Config
    private lateinit var launchesCache: LaunchesCache

    private var repository: LaunchesRepository? = null
    private var launchDetailsInteractor: LaunchDetailsInteractor? = null
    private var launchesInteractor: LaunchesInteractor? = null
    private var searchResultsInteractor: SearchResultsInteractor? = null

    fun initialize(app: Application, configuration: DI.Config = DI.Config.RELEASE) {
        config = configuration
        launchesCache = LaunchesCacheImpl(app)
    }

    fun getLaunchesInteractorImpl(): LaunchesInteractor {
        if (config == DI.Config.RELEASE && launchesInteractor == null)
            launchesInteractor = makeLaunchesInteractor(getLaunchesRepository())
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
            searchResultsInteractor = SearchResultsInteractorImpl(getLaunchesRepository())
        return searchResultsInteractor!!
    }

    fun getLaunchDetailsInteractor(): LaunchDetailsInteractor {
        if (launchDetailsInteractor == null)
            launchDetailsInteractor = getLaunchDetailsInteractor(getLaunchesRepository())
        return launchDetailsInteractor!!
    }

    //region private methods

    private fun getLaunchDetailsInteractor(repository: LaunchesRepository) =
        LaunchDetailsInteractorImpl(repository)

    private fun makeLaunchesInteractor(repository: LaunchesRepository) =
        LaunchesInteractorImpl(repository, YearValidator())

    private fun getLaunchesRepository(): LaunchesRepository {
        if (repository == null)
            repository = LaunchesRepositoryImpl(getLaunchesDataStoreFactory(), LaunchDataMapper())
        return repository!!
    }

    private fun getDiskLaunchesDataStore() = DiskLaunchesDataStore(launchesCache)
    private fun getCloudLaunchesDataStore() = CloudLaunchesDataStore(
        NetworkModule.connectionManager,
        NetworkModule.getService(LaunchesService::class.java),
        launchesCache
    )

    private fun getLaunchesDataStoreFactory() =
        LaunchesDataStoreFactoryImpl(launchesCache, getDiskLaunchesDataStore(), getCloudLaunchesDataStore())

    //endregion
}