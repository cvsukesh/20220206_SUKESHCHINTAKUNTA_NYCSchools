package com.codingchallenge.nycschools.mvvm

import androidx.annotation.VisibleForTesting
import com.codingchallenge.nycschools.network.*
import com.codingchallenge.nycschools.room.NycSchoolDao
import com.codingchallenge.nycschools.room.model.NycSchool
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

open class NycSchoolsRepository @Inject constructor(
    var networkTaskManager: DataRequestManager,
    var nycSchoolApi: NycSchoolApi,
    var nycSchoolDao: NycSchoolDao
) {

    open fun getNycHighSchoolRecords(): Observable<DataResponse<List<NycSchool>>> =
        networkTaskManager.getData(object : DataRequest<List<NycSchool>>() {
            override fun getNetworkObservable(): Observable<List<NycSchool>> {
                return nycSchoolApi.getNycHighSchoolRecordsAPI()
            }
        })

    open fun getSatRecordsForNycHighSchool(): Observable<DataResponse<List<NycSchool>>> =
        networkTaskManager.getData(object : DataRequest<List<NycSchool>>() {
            override fun getNetworkObservable(): Observable<List<NycSchool>> {
                return nycSchoolApi.getSatScoresOfNycHighSchoolsAPI()
            }
        })

    fun insertNycHighSchoolRecords(listOfRecords: List<NycSchool>?) {
        clearAllTheRecords()
        listOfRecords?.forEach { nycSchool ->
            nycSchoolDao.insertRecords(nycSchool)
        }
    }

    fun updateNycSchoolRecords(listOfRecords: List<NycSchool>?) {
        listOfRecords?.forEach { nycSchool ->
            nycSchoolDao.updateRecords(nycSchool)
        }
    }

    fun getAllRecords(): Observable<List<NycSchool>> = nycSchoolDao.getAllRecords()

    fun getHighSchool(id: String): Observable<NycSchool> = nycSchoolDao.getRecordFromId(id)

    private fun clearAllTheRecords() = nycSchoolDao.dropAllTheRecords()
}