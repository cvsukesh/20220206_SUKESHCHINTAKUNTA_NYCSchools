package com.codingchallenge.nycschools.network

import com.codingchallenge.nycschools.room.model.NycSchool
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface NycSchoolApi {

    @GET("s3k6-pzi2.json")
    fun getNycHighSchoolRecordsAPI(): Observable<List<NycSchool>>

    @GET("f9bf-2cp4.json")
    fun getSatScoresOfNycHighSchoolsAPI(): Observable<List<NycSchool>>
}