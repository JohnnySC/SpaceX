package com.github.johnnysc.data.repository.datasource

import com.github.johnnysc.data.cache.LaunchesCache
import com.github.johnnysc.data.entity.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * @see DiskLaunchesDataStore
 *
 * @author Asatryan on 01.06.19
 */
class DiskLaunchesDataStoreTest {

    private companion object {
        const val STUB_YEAR = "2019"
    }

    private lateinit var diskLaunchesDataStore: LaunchesDataStore
    private lateinit var launchesCache: LaunchesCache

    @Before
    fun setUp() {
        launchesCache = mock(LaunchesCache::class.java)
        diskLaunchesDataStore = DiskLaunchesDataStore(launchesCache)
    }

    @Test
    fun testEmptyList() {
        `when`(launchesCache.get(STUB_YEAR)).thenReturn(emptyList())

        runBlocking {
            val actual = diskLaunchesDataStore.getLaunchEntityList(STUB_YEAR)

            assertNotNull(actual)
            assertThat(actual.size, `is`(0))
        }
    }

    @Test
    fun testOneItemList() {
        `when`(launchesCache.get(STUB_YEAR)).thenReturn(listOf(getItem()))

        runBlocking {
            val actual = diskLaunchesDataStore.getLaunchEntityList(STUB_YEAR)

            assertNotNull(actual)
            assertThat(actual.size, `is`(1))
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testDetailsOnEmptyList() {
        `when`(launchesCache.get(STUB_YEAR)).thenReturn(emptyList())

        runBlocking {
            diskLaunchesDataStore.getLaunchDetails(STUB_YEAR, 0)
        }
    }

    @Test
    fun testDetailsOnSingleItemList() {
        `when`(launchesCache.get(STUB_YEAR)).thenReturn(listOf(getItem()))

        runBlocking {
            val actual = diskLaunchesDataStore.getLaunchDetails(STUB_YEAR, 0)

            assertNotNull(actual)
            assertThat(actual.flightNumber, `is`(123))
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testDetailsOfWrongId() {
        `when`(launchesCache.get(STUB_YEAR)).thenReturn(listOf(getItem()))

        runBlocking {
            diskLaunchesDataStore.getLaunchDetails(STUB_YEAR, 1)
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