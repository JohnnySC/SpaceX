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

    companion object {
        const val STUB_YEAR = "2018"
    }

    private lateinit var repository: LaunchesRepositoryImpl
    private lateinit var dataStoreFactory: LaunchesDataStoreFactory
    private lateinit var mapper: LaunchDataMapper

    @Before
    fun setUp() {
        dataStoreFactory = TestDataStoreFactory()
        mapper = LaunchDataMapper()
        repository = LaunchesRepositoryImpl(
            dataStoreFactory,
            mapper
        )
    }

    @Test
    fun testPositiveCaseReloadTrue() {
        runBlocking {
            val actualList = repository.getLaunches(STUB_YEAR, true)

            assertNotNull(actualList)
            assertThat(actualList, `is`(not(emptyList())))
            assertThat(actualList.size, `is`(1))

            val actualData = actualList.first()
            assertThat(actualData, `is`(instanceOf(LaunchData::class.java)))
            assertThat(actualData, `is`(getData(false)))
        }
    }

    @Test
    fun testPositiveCaseReloadFalse() {
        runBlocking {
            val actualList = repository.getLaunches(STUB_YEAR)

            assertNotNull(actualList)
            assertThat(actualList, `is`(not(emptyList())))
            assertThat(actualList.size, `is`(1))

            val actualData = repository.getLaunchData(STUB_YEAR, 0)
            assertThat(actualData, `is`(instanceOf(LaunchData::class.java)))
            assertThat(actualData, `is`(getData(true)))
        }
    }

    private fun getData(cached: Boolean) = LaunchData(
        123,
        if (cached) "impossibleCached" else "impossibleCloud",
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
}

class TestDataStore(private val fromCloud: Boolean) : LaunchesDataStore {
    override suspend fun getLaunchEntityList(year: String) = listOf(getEntity(fromCloud))

    override suspend fun getLaunchDetails(year: String, id: Int) =
        getLaunchEntityList(year)[id]

    private fun getEntity(fromCloud: Boolean) = LaunchesEntity(
        123,
        if (fromCloud) "impossibleCloud" else "impossibleCached",
        LaunchesRepositoryImplTest.STUB_YEAR.toInt(),
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
    override fun create(year: String, priority: LaunchesDataStoreFactory.Priority) =
        TestDataStore(priority == LaunchesDataStoreFactory.Priority.CLOUD)
}