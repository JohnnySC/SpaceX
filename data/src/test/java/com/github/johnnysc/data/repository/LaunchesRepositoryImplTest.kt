package com.github.johnnysc.data.repository

import com.github.johnnysc.data.entity.*
import com.github.johnnysc.data.entity.mapper.LaunchDataMapper
import com.github.johnnysc.data.repository.datasource.LaunchesDataStore
import com.github.johnnysc.data.repository.datasource.LaunchesDataStoreFactory
import com.github.johnnysc.domain.FirstStageData
import com.github.johnnysc.domain.LaunchData
import com.github.johnnysc.domain.RocketData
import com.github.johnnysc.domain.SecondStageData
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @see LaunchesRepositoryImpl
 *
 * @author Asatryan on 02.06.19
 */
class LaunchesRepositoryImplTest {

    private companion object {
        const val STUB_YEAR = "2018"
    }

    private lateinit var repository: LaunchesRepositoryImpl
    private lateinit var dataStore: LaunchesDataStore
    private lateinit var dataStoreFactory: LaunchesDataStoreFactory
    private lateinit var mapper: LaunchDataMapper

    @Before
    fun setUp() {
        dataStore = TestDataStore()
        dataStoreFactory = TestDataStoreFactory()
        mapper = LaunchDataMapper()
        repository = LaunchesRepositoryImpl(
            dataStoreFactory,
            mapper
        )
    }

    @Test
    fun testPositiveCase() {
        runBlocking {
            val actualList = repository.getLaunches(STUB_YEAR)

            assertNotNull(actualList)
            assertThat(actualList, `is`(not(emptyList())))
            assertThat(actualList.size, `is`(1))

            val actualData = repository.getLaunchData(STUB_YEAR, 0)
            assertThat(actualData, `is`(instanceOf(LaunchData::class.java)))
            assertThat(actualData, `is`(getData()))
        }
    }

    private fun getData() = LaunchData(
        123,
        "impossible",
        STUB_YEAR.toInt(),
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

    class TestDataStore : LaunchesDataStore {
        override suspend fun getLaunchEntityList(year: String) = listOf(getEntity())

        override suspend fun getLaunchDetails(year: String, id: Int) =
            getLaunchEntityList(year)[id]

        private fun getEntity() = LaunchesEntity(
            123,
            "impossible",
            STUB_YEAR.toInt(),
            "2018-09-15",
            RocketEntity("falcon", "heavy", FirstStageEntity(emptyList()), SecondStageEntity(2, emptyList())),
            emptyList(),
            emptyMap(),
            emptyMap(),
            LaunchSiteEntity("usa"),
            true,
            emptyMap(),
            "details",
            false,
            "2018-09-15",
            null
        )
    }

    class TestDataStoreFactory : LaunchesDataStoreFactory {
        override fun create(year: String) = TestDataStore()
    }
}