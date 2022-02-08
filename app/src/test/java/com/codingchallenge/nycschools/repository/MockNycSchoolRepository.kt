package com.codingchallenge.nycschools.repository

import com.codingchallenge.nycschools.mvvm.NycSchoolsRepository
import com.codingchallenge.nycschools.network.DataError
import com.codingchallenge.nycschools.network.DataResponse
import com.codingchallenge.nycschools.network.NycSchoolApi
import com.codingchallenge.nycschools.room.NycSchoolDao
import com.codingchallenge.nycschools.room.model.NycSchool
import com.codingchallenge.nycschools.utils.AppTestUtils
import io.reactivex.rxjava3.core.Observable

class MockNycSchoolRepository(
    testDataRequestManager: TestDataRequestManager,
    nycSchoolApi: NycSchoolApi,
    nycSchoolDao: NycSchoolDao
) : NycSchoolsRepository(testDataRequestManager, nycSchoolApi, nycSchoolDao) {

    var isSuccess = false

    override fun getNycHighSchoolRecords(): Observable<DataResponse<List<NycSchool>>> {
        if (isSuccess) {
            val list: ArrayList<NycSchool> = AppTestUtils.readJsonResponseFileForList("appConfig/nyc_high_school_list.json")
            return Observable.just(DataResponse.SUCCESS(list))
        }
        return Observable.just(DataResponse.ERROR(DataError("", "Error", "Something error")))
    }

    override fun getSatRecordsForNycHighSchool(): Observable<DataResponse<List<NycSchool>>> {
        if (isSuccess) {
            val list: ArrayList<NycSchool> = AppTestUtils.readJsonResponseFileForList("appConfig/nyc_school_list_with_sat_scores.json")
            return Observable.just(DataResponse.SUCCESS(list))
        }
        return Observable.just(DataResponse.ERROR(DataError("", "Error", "Something error")))
    }
}