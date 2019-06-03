package com.github.johnnysc.spacex.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.johnnysc.domain.interactor.LaunchesInteractor
import com.github.johnnysc.domain.interactor.Status
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.di.DI
import com.github.johnnysc.spacex.di.MainScreenModule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Rule

/**
 * @author Asatryan on 02.06.19
 */
class MainScreenViewModelTest {

    private lateinit var interactor: LaunchesInteractor
    private lateinit var viewModel: MainScreenViewModel

    private companion object {
        const val STUB_YEAR = "2019"
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        val application = mock(Application::class.java)
        DI.initialize(application, DI.Config.TEST)
        MainScreenModule.setLaunchesInteractor(mock(LaunchesInteractor::class.java))
        interactor = MainScreenModule.getLaunchesInteractorImpl()

        viewModel = MainScreenViewModel()
        viewModel.errorState = mock(MutableLiveDataInt::class.java)
        viewModel.searchState = mock(MutableLiveDataPair::class.java)
        viewModel.progressState = mock(MutableLiveDataBoolean::class.java)
    }

    @Test
    fun testInvalidInputData() {
        runBlocking {
            `when`(interactor.isInputDataValid(STUB_YEAR)).thenReturn(false)

            for (i in 0..10)
                viewModel.fetch(STUB_YEAR)

            delay(500)

            verify(viewModel.errorState, times(1)).postValue(anyInt())
            verify(viewModel.searchState, never()).postValue(any())
            verify(viewModel.progressState, never()).postValue(any())
        }
    }

    @Test
    fun testInputDataIsNotEnough() {
        runBlocking {
            `when`(interactor.isInputDataValid(STUB_YEAR)).thenReturn(null)

            for (i in 0..10)
                viewModel.fetch(STUB_YEAR)

            delay(500)

            verify(viewModel.progressState, never()).postValue(any())
            verify(viewModel.searchState, never()).postValue(any())
            verify(viewModel.progressState, never()).postValue(any())
        }
    }

    @Test
    fun testNoResults() {
        runBlocking {
            `when`(interactor.isInputDataValid(STUB_YEAR)).thenReturn(true)

            interactor.stub {
                onBlocking { fetch(STUB_YEAR) }.doReturn(Status.NO_RESULTS)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_YEAR)

            delay(500)


            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(Pair(R.id.no_results, STUB_YEAR))
        }
    }

    @Test
    fun testNoConnection() {
        runBlocking {
            `when`(interactor.isInputDataValid(STUB_YEAR)).thenReturn(true)

            interactor.stub {
                onBlocking { fetch(STUB_YEAR) }.doReturn(Status.NO_CONNECTION)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_YEAR)

            delay(500)


            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(Pair(R.id.no_connection, STUB_YEAR))
        }
    }

    @Test
    fun testServiceUnavailable() {
        runBlocking {
            `when`(interactor.isInputDataValid(STUB_YEAR)).thenReturn(true)

            interactor.stub {
                onBlocking { fetch(STUB_YEAR) }.doReturn(Status.SERVICE_UNAVAILABLE)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_YEAR)

            delay(500)


            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(Pair(R.id.service_unavailable, STUB_YEAR))
        }
    }

    @Test
    fun testSuccess() {
        runBlocking {
            `when`(interactor.isInputDataValid(STUB_YEAR)).thenReturn(true)

            interactor.stub {
                onBlocking { fetch(STUB_YEAR) }.doReturn(Status.SUCCESS)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_YEAR)

            delay(500)


            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(Pair(R.id.go_to_search_results, STUB_YEAR))
        }
    }

    @Test
    fun testDelay() {
        runBlocking {
            `when`(interactor.isInputDataValid(STUB_YEAR)).thenReturn(true)

            interactor.stub {
                onBlocking { fetch(STUB_YEAR) }.doReturn(Status.SUCCESS)
            }

            for (i in 0..5) {
                viewModel.fetch(STUB_YEAR)
                delay((i * 100).toLong())
            }

            delay(500)

            verify(viewModel.progressState, times(2)).postValue(true)
            verify(viewModel.progressState, times(2)).postValue(false)
            verify(viewModel.searchState, times(2)).postValue(Pair(R.id.go_to_search_results, STUB_YEAR))
        }
    }
}

class MutableLiveDataInt : MutableLiveData<Int>()
class MutableLiveDataPair : MutableLiveData<Pair<Int, String?>>()
class MutableLiveDataBoolean : MutableLiveData<Boolean>()