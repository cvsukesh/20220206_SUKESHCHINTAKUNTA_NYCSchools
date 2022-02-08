package com.codingchallenge.nycschools.repository

import androidx.test.core.app.ApplicationProvider
import com.codingchallenge.nycschools.network.DataResponse
import com.codingchallenge.nycschools.network.NycSchoolApi
import com.codingchallenge.nycschools.room.NycSchoolDao
import com.codingchallenge.nycschools.room.model.NycSchool
import com.codingchallenge.nycschools.utils.AppLibUtils
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NycHighSchoolRepositoryTest {

    @Mock
    lateinit var nycSchoolApi: NycSchoolApi

    lateinit var repository: MockNycSchoolRepository

    @Mock
    lateinit var nycSchoolDao: NycSchoolDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        AppLibUtils.get().setApplicationContext(ApplicationProvider.getApplicationContext())
        repository = MockNycSchoolRepository(TestDataRequestManager(), nycSchoolApi, nycSchoolDao)
    }

    @After
    fun tearDown() {
        // Any operations after the test.
    }

    @Test
    fun test_fetxh_emitsHighSchoolSuccessResponse() {
        repository.isSuccess = true
        repository.getNycHighSchoolRecords().subscribe({ dataResponse ->
            when (dataResponse) {
                is DataResponse.SUCCESS -> {
                    Assert.assertTrue(dataResponse.value is ArrayList<NycSchool>)
                }
                else -> {
                    Assert.assertFalse(true)
                }
            }
        }, {
            Assert.assertFalse(true)
        })
    }

    @Test
    fun test_fetxh_emitsHighSchoolErrorResponse() {
        repository.isSuccess = false
        repository.getNycHighSchoolRecords().subscribe({ dataResponse ->
            when (dataResponse) {
                is DataResponse.SUCCESS -> {
                    Assert.assertTrue(false)
                }
                is DataResponse.ERROR -> {
                    Assert.assertTrue(true)
                }
            }
        }, {
            Assert.assertTrue(true)
        })
    }

    @Test
    fun test_fetxh_emitsHighSchoolSatScoresSuccessResponse() {
        repository.isSuccess = true
        repository.getSatRecordsForNycHighSchool().subscribe({ dataResponse ->
            when (dataResponse) {
                is DataResponse.SUCCESS -> {
                    Assert.assertTrue(dataResponse.value is ArrayList<NycSchool>)
                }
                else -> {
                    Assert.assertTrue(false)
                }
            }
        }, {
            Assert.assertTrue(false)
        })
    }

    @Test
    fun test_fetxh_emitsHighSchoolSatScoreErrorResponse() {
        repository.isSuccess = false
        repository.getSatRecordsForNycHighSchool().subscribe({ dataResponse ->
            when (dataResponse) {
                is DataResponse.SUCCESS -> {
                    Assert.assertTrue(false)
                }
                else -> {
                    Assert.assertTrue(true)
                }
            }
        }, {
            Assert.assertTrue(true)
        })
    }


}