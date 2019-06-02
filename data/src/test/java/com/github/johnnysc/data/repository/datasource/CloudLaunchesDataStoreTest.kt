package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.cache.LaunchesCache
import com.github.johnnysc.data.entity.*
import com.github.johnnysc.data.net.ConnectionManager
import com.github.johnnysc.data.net.LaunchesService
import com.github.johnnysc.domain.exception.NetworkConnectionException
import com.github.johnnysc.domain.exception.ServerUnavailableException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import retrofit2.Response

/**
 * @see CloudLaunchesDataStore
 *
 * @author Asatryan on 01.06.19
 */
class CloudLaunchesDataStoreTest {

    private companion object {
        const val STUB_YEAR = "2019"
    }

    private lateinit var cloudLaunchesDataStore: LaunchesDataStore
    private lateinit var connectionManager: ConnectionManager
    private lateinit var launchesService: LaunchesService
    private lateinit var launchesCache: LaunchesCache

    @Before
    fun setUp() {
        connectionManager = mock(ConnectionManager::class.java)
        launchesService = mock(LaunchesService::class.java)
        launchesCache = mock(LaunchesCache::class.java)
        cloudLaunchesDataStore = CloudLaunchesDataStore(
            connectionManager,
            launchesService,
            launchesCache
        )
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testGetDetailsFromCloud() {
        runBlocking { cloudLaunchesDataStore.getLaunchDetails(STUB_YEAR, 0) }
    }

    @Test(expected = NetworkConnectionException::class)
    fun testNoNetwork() {
        `when`(connectionManager.isNetworkAbsent()).thenReturn(true)

        runBlocking { cloudLaunchesDataStore.getLaunchEntityList(STUB_YEAR) }
    }

    @Test(expected = ServerUnavailableException::class)
    fun testHandlingExceptionWhileGettingData() {
        `when`(connectionManager.isNetworkAbsent()).thenReturn(false)
        doThrow(KotlinNullPointerException::class.java)
            .`when`(launchesService)
            .getLaunchesAsync(ArgumentMatchers.anyString())
        runBlocking { cloudLaunchesDataStore.getLaunchEntityList(STUB_YEAR) }
    }

    @Test(expected = ServerUnavailableException::class)
    fun testErrorResponse() {
        `when`(connectionManager.isNetworkAbsent()).thenReturn(false)

        runBlocking {
            val response = Response.error<List<LaunchesEntity>>(404, mock(ResponseBody::class.java))
            `when`(launchesService.getLaunchesAsync(STUB_YEAR)).thenReturn(response.toDeferred())

            cloudLaunchesDataStore.getLaunchEntityList(STUB_YEAR)
        }
    }

    @Test(expected = ServerUnavailableException::class)
    fun testNullResponse() {
        `when`(connectionManager.isNetworkAbsent()).thenReturn(false)

        runBlocking {
            val response = Response.success<List<LaunchesEntity>>(null)
            `when`(launchesService.getLaunchesAsync(STUB_YEAR)).thenReturn(response.toDeferred())

            cloudLaunchesDataStore.getLaunchEntityList(STUB_YEAR)
        }
    }

    @Test
    fun testSuccessfulResponse() {
        `when`(connectionManager.isNetworkAbsent()).thenReturn(false)

        runBlocking {
            val response = Response.success<List<LaunchesEntity>>(emptyList())
            `when`(launchesService.getLaunchesAsync(STUB_YEAR)).thenReturn(response.toDeferred())

            val result = cloudLaunchesDataStore.getLaunchEntityList(STUB_YEAR)

            assertNotNull(result)
        }
    }

    @Test
    fun testSuccessfulResponseWithItem() {
        `when`(connectionManager.isNetworkAbsent()).thenReturn(false)

        runBlocking {
            val response = Response.success(listOf(getItem()))
            `when`(launchesService.getLaunchesAsync(STUB_YEAR)).thenReturn(response.toDeferred())

            val result = cloudLaunchesDataStore.getLaunchEntityList(STUB_YEAR)

            assertNotNull(result)
            assertThat(result.size, `is`(1))
            assertThat(result.first(), `is`(instanceOf(LaunchesEntity::class.java)))
            assertThat(result.first().launchYear.toString(), `is`(STUB_YEAR))
        }
    }

    private fun getItem() = LaunchesEntity(
        123,
        "impossible",
        STUB_YEAR.toInt(),
        "01.01.2019",
        RocketEntity("falcon", "heavy", FirstStageEntity(emptyList()), SecondStageEntity(2, emptyList())),
        emptyList(),
        emptyMap(),
        emptyMap(),
        LaunchSiteEntity("usa"),
        true,
        emptyMap(),
        "details",
        false,
        "01.01.2019",
        null
    )
}

fun <T> T.toDeferred() = GlobalScope.async { this@toDeferred }