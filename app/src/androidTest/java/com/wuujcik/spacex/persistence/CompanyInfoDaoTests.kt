package com.wuujcik.spacex.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuujcik.spacex.persistence.companyInfo.CompanyInfo
import com.wuujcik.spacex.persistence.companyInfo.CompanyInfoDatabase
import com.wuujcik.spacex.persistence.companyInfo.Headquarters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Rule


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CompanyInfoDaoTests {

    private lateinit var database: CompanyInfoDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CompanyInfoDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertItemAndGetCompanyInfo() = runBlockingTest {
        // GIVEN - insert a task
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
        database.companyInfoDao().insert(item)

        // WHEN - Get the task by id from the database
        val loaded = database.companyInfoDao().getOne()

        // THEN - The loaded data contains the expected values
        MatcherAssert.assertThat<CompanyInfo>(loaded as CompanyInfo, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded.id, Is.`is`(item.id))
        MatcherAssert.assertThat(loaded.name, Is.`is`(item.name))
        MatcherAssert.assertThat(loaded.founder, Is.`is`(item.founder))
        MatcherAssert.assertThat(loaded.founded, Is.`is`(item.founded))
        MatcherAssert.assertThat(loaded.employees, Is.`is`(item.employees))
        MatcherAssert.assertThat(loaded.vehicles, Is.`is`(item.vehicles))
        MatcherAssert.assertThat(loaded.launch_sites, Is.`is`(item.launch_sites))
        MatcherAssert.assertThat(loaded.test_sites, Is.`is`(item.test_sites))
        MatcherAssert.assertThat(loaded.ceo, Is.`is`(item.ceo))
        MatcherAssert.assertThat(loaded.cto, Is.`is`(item.cto))
        MatcherAssert.assertThat(loaded.coo, Is.`is`(item.coo))
        MatcherAssert.assertThat(loaded.cto_propulsion, Is.`is`(item.cto_propulsion))
        MatcherAssert.assertThat(loaded.valuation, Is.`is`(item.valuation))
        MatcherAssert.assertThat(loaded.headquarters, Is.`is`(item.headquarters))
        MatcherAssert.assertThat(loaded.summary, Is.`is`(item.summary))
    }

    @Test
    fun insertAndUpdate() = runBlockingTest {
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
        database.companyInfoDao().insert(item)

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
        database.companyInfoDao().insert(updatedItem)

        // THEN
        val result = database.companyInfoDao().getOne()
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(updatedItem))
    }
}
