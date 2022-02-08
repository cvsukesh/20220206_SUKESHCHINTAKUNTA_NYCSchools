package com.codingchallenge.nycschools.room

import androidx.room.*
import com.codingchallenge.nycschools.room.model.NycSchool
import io.reactivex.rxjava3.core.Observable

@Dao
interface NycSchoolDao {

    @Query("SELECT * from nyc_high_school")
    fun getAllRecords() : Observable<List<NycSchool>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecords(nycSchool: NycSchool)

    @Update
    fun updateRecords(nycSchool: NycSchool)

    @Query("DELETE FROM nyc_high_school")
    fun dropAllTheRecords()

    @Query("SELECT * from nyc_high_school WHERE id=:schoolId")
    fun getRecordFromId(schoolId: String) : Observable<NycSchool>
}