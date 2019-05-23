package com.github.johnnysc.spacex.di

import com.github.johnnysc.spacex.BuildConfig
import com.github.johnnysc.spacex.data.*
import com.github.johnnysc.spacex.data.cache.CacheManager
import com.github.johnnysc.spacex.data.cache.CacheManagerImpl
import com.github.johnnysc.spacex.data.network.ConnectionManager
import com.github.johnnysc.spacex.data.network.ConnectionManagerImpl
import com.github.johnnysc.spacex.data.network.LaunchesService
import com.github.johnnysc.spacex.data.repo.LaunchesRepositoryImpl
import com.github.johnnysc.spacex.domain.LaunchesRepository
import com.github.johnnysc.spacex.domain.entity.LaunchData
import com.github.johnnysc.spacex.domain.interactor.launch.LaunchesInteractor
import com.github.johnnysc.spacex.domain.interactor.launch.LaunchesInteractorImpl
import com.github.johnnysc.spacex.domain.interactor.launch.details.LaunchDetailsInteractor
import com.github.johnnysc.spacex.domain.interactor.launch.details.LaunchDetailsInteractorImpl
import com.github.johnnysc.spacex.domain.interactor.search.SearchResultsInteractor
import com.github.johnnysc.spacex.domain.interactor.search.SearchResultsInteractorImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.spacexdata.com/v2/"

val interactorModule: Module = module {
    //********************** LaunchesInteractor **********************

    single {
        LaunchesInteractorImpl(
            get(),
            ConnectionManagerImpl(androidApplication().applicationContext),
            YearValidator()
        ) as LaunchesInteractor
    }

    //********************** SearchResultsInteractor **********************

    single {
        SearchResultsInteractorImpl(
            get(),
            SearchResultsMapper()
        ) as SearchResultsInteractor
    }

    //********************** SearchResultsInteractor **********************

    single {
        LaunchDetailsInteractorImpl(
            get(),
            LaunchDataMapper()
        ) as LaunchDetailsInteractor
    }
}

val repoModule: Module = module {
    single {
        LaunchesRepositoryImpl(get(), get()) as LaunchesRepository
    }

    factory {
        val interceptor: Interceptor = HttpLoggingInterceptor().also {
            it.level =
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
        }

        val client: OkHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        retrofit.create(LaunchesService::class.java) as LaunchesService
    }

    factory {
        CacheManagerImpl(androidApplication().applicationContext) as CacheManager
    }
}