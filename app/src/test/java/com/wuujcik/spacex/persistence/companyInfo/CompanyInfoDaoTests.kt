package com.wuujcik.spacex.persistence.companyInfo

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class CompanyInfoDaoTests {

    private lateinit var database: RoomDatabase
    private lateinit var companyInfoDao: CompanyInfoDao

    @Before
    fun initDbAndDao() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CompanyInfoDatabase::class.java
        ).build()
        companyInfoDao = (database as CompanyInfoDatabase).companyInfoDao()
    }


    @After
    fun closeDb() = database.close()

    @Test
    fun insertItemAndGetById() = runBlocking {
        // GIVEN
        val headquarters = Headquarters(142, "address", "city", "state")
        val item = CompanyInfo(
            "id",
            "name",
            "founder",
            2000,
            7000,
            7,
            3,
            4,
            "CEO",
            "CTO",
            "COO",
            "CTO Propulsion",
            1000000L,
            headquarters,
            "some summary"
        )
        companyInfoDao.insert(item)

        // WHEN
        val loaded = companyInfoDao.getOne()

        // THEN
        MatcherAssert.assertThat<CompanyInfo>(loaded as CompanyInfo, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.id, `is`(item.id))
        MatcherAssert.assertThat(loaded.name, `is`(item.name))
        MatcherAssert.assertThat(loaded.founder, `is`(item.founder))
        MatcherAssert.assertThat(loaded.founded, `is`(item.founded))
        MatcherAssert.assertThat(loaded.employees, `is`(item.employees))
        MatcherAssert.assertThat(loaded.vehicles, `is`(item.vehicles))
        MatcherAssert.assertThat(loaded.launch_sites, `is`(item.launch_sites))
        MatcherAssert.assertThat(loaded.test_sites, `is`(item.test_sites))
        MatcherAssert.assertThat(loaded.ceo, `is`(item.ceo))
        MatcherAssert.assertThat(loaded.cto, `is`(item.cto))
        MatcherAssert.assertThat(loaded.coo, `is`(item.coo))
        MatcherAssert.assertThat(loaded.cto_propulsion, `is`(item.cto_propulsion))
        MatcherAssert.assertThat(loaded.valuation, `is`(item.valuation))
        MatcherAssert.assertThat(loaded.headquarters, `is`(item.headquarters))
        MatcherAssert.assertThat(loaded.summary, `is`(item.summary))
    }


    @Test
    @Throws(Exception::class)
    fun insertAndUpdate() = runBlocking {
        // GIVEN
        val headquarters = Headquarters(142, "address", "city", "state")
        val item = CompanyInfo(
            "id",
            "name",
            "founder",
            2000,
            7000,
            7,
            3,
            4,
            "CEO",
            "CTO",
            "COO",
            "CTO Propulsion",
            1000000L,
            headquarters,
            "some summary"
        )
        companyInfoDao.insert(item)

        // WHEN
        val updatedItem = CompanyInfo(
            "id",
            "updated_name",
            "updated_founder",
            2001,
            7001,
            8,
            4,
            5,
            "updated_CEO",
            "updated_CTO",
            "updated_COO",
            "updated_CTO Propulsion",
            2000000L,
            headquarters,
            "updated summary"
        )
        companyInfoDao.insert(updatedItem)

        // THEN
        val result = companyInfoDao.getOne()
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(updatedItem))
    }
}
