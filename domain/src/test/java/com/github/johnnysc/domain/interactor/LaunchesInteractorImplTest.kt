package com.github.johnnysc.domain.interactor

import com.github.johnnysc.domain.FirstStageData
import com.github.johnnysc.domain.LaunchData
import com.github.johnnysc.domain.RocketData
import com.github.johnnysc.domain.SecondStageData
import com.github.johnnysc.domain.exception.NetworkConnectionException
import com.github.johnnysc.domain.exception.ServerUnavailableException
import com.github.johnnysc.domain.repository.LaunchesRepository
import com.github.johnnysc.domain.validator.YearValidator
import org.junit.Assert.*
import org.junit.Test
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import java.lang.UnsupportedOperationException

/**
 * @see LaunchesInteractorImpl
 *
 * @author Asatryan on 02.06.19
 */
class LaunchesInteractorImplTest {

    private companion object {
        const val STUB_YEAR = "2019"
    }

    private lateinit var interactor: LaunchesInteractor
    private lateinit var validator: YearValidator

    @Before
    fun setUp() {
        validator = YearValidator()
    }

    @Test
    fun testFetchSuccessWithNoResults() {
        interactor = LaunchesInteractorImpl(EmptyListRepositoryImpl(), validator)
        runBlocking {
            val actual = interactor.fetch(STUB_YEAR)

            assertThat(actual, `is`(Status.NO_RESULTS))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = LaunchesInteractorImpl(OneItemListRepositoryImpl(), validator)
        runBlocking {
            val actual = interactor.fetch(STUB_YEAR)

            assertThat(actual, `is`(Status.SUCCESS))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = LaunchesInteractorImpl(NetworkExceptionRepositoryImpl(), validator)
        runBlocking {
            val actual = interactor.fetch(STUB_YEAR)

            assertThat(actual, `is`(Status.NO_CONNECTION))
        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = LaunchesInteractorImpl(ServiceUnavailableExceptionRepositoryImpl(), validator)
        runBlocking {
            val actual = interactor.fetch(STUB_YEAR)

            assertThat(actual, `is`(Status.SERVICE_UNAVAILABLE))
        }
    }
}

class EmptyListRepositoryImpl : LaunchesRepository {
    override suspend fun getLaunches(year: String, reload: Boolean) = emptyList<LaunchData>()
    override suspend fun getLaunchData(year: String, id: Int) =
        throw UnsupportedOperationException("no need to use this here")
}

class OneItemListRepositoryImpl : LaunchesRepository {
    override suspend fun getLaunches(year: String, reload: Boolean) = listOf(getData())
    override suspend fun getLaunchData(year: String, id: Int) =
        throw UnsupportedOperationException("no need to use this here")

    private fun getData() = LaunchData(
        123,
        "impossible",
        2019,
        "15-09-2018 at 00:00",
        RocketData("falcon", "heavy", FirstStageData(emptyList()), SecondStageData(2, emptyList())),
        emptyList(),
        emptyList(),
        emptyMap(),
        "usa",
        true,
        emptyList(),
        emptyList(),
        emptyList(),
        "details",
        false,
        "15-09-2018 at 00:00",
        null
    )
}

class NetworkExceptionRepositoryImpl : LaunchesRepository {
    override suspend fun getLaunches(year: String, reload: Boolean) = throw NetworkConnectionException()
    override suspend fun getLaunchData(year: String, id: Int) =
        throw UnsupportedOperationException("no need to use this here")
}

class ServiceUnavailableExceptionRepositoryImpl : LaunchesRepository {
    override suspend fun getLaunches(year: String, reload: Boolean) = throw ServerUnavailableException()
    override suspend fun getLaunchData(year: String, id: Int) =
        throw UnsupportedOperationException("no need to use this here")
}