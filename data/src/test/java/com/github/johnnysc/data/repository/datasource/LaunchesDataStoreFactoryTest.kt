package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.cache.LaunchesCache
import com.github.johnnysc.data.net.ConnectionManager
import com.github.johnnysc.data.net.LaunchesService
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * @see LaunchesDataStoreFactoryImpl
 *
 * @author Asatryan on 01.06.19
 */
class LaunchesDataStoreFactoryTest {

    private companion object {
        const val STUB_YEAR = "2019"
    }

    private lateinit var launchesDataStoreFactory: LaunchesDataStoreFactoryImpl
    private lateinit var launchesCache: LaunchesCache

    @Before
    fun setUp() {
        launchesCache = mock(LaunchesCache::class.java)
        launchesDataStoreFactory = LaunchesDataStoreFactoryImpl(
            launchesCache,
            DiskLaunchesDataStore(launchesCache),
            CloudLaunchesDataStore(
                mock(ConnectionManager::class.java),
                mock(LaunchesService::class.java),
                launchesCache
            )
        )
    }

    @Test
    fun testDiskDataSourceFactoryWhenCacheRequiredAndDataCached() {
        Mockito.`when`(launchesCache.isCached(STUB_YEAR)).thenReturn(true)

        val actual = launchesDataStoreFactory.create(STUB_YEAR, LaunchesDataStoreFactory.Priority.CACHE)

        assertThat(actual, `is`(instanceOf(DiskLaunchesDataStore::class.java)))
    }

    @Test
    fun testCloudDataSourceFactoryWhenCloudRequiredAndDataNotCached() {
        Mockito.`when`(launchesCache.isCached(STUB_YEAR)).thenReturn(true)

        val actual = launchesDataStoreFactory.create(STUB_YEAR, LaunchesDataStoreFactory.Priority.CLOUD)

        assertThat(actual, `is`(instanceOf(CloudLaunchesDataStore::class.java)))
    }

    @Test
    fun testCloudDataSourceFactoryWhenCacheRequiredButDataNotCached() {
        Mockito.`when`(launchesCache.isCached(STUB_YEAR)).thenReturn(false)

        val actual = launchesDataStoreFactory.create(STUB_YEAR, LaunchesDataStoreFactory.Priority.CACHE)

        assertThat(actual, `is`(instanceOf(CloudLaunchesDataStore::class.java)))
    }

    @Test
    fun testCloudDataSourceFactoryWhenCloudRequiredAndDataCached() {
        Mockito.`when`(launchesCache.isCached(STUB_YEAR)).thenReturn(false)

        val actual = launchesDataStoreFactory.create(STUB_YEAR, LaunchesDataStoreFactory.Priority.CLOUD)

        assertThat(actual, `is`(instanceOf(CloudLaunchesDataStore::class.java)))
    }
}