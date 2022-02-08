package com.codingchallenge.nycschools.network

import androidx.annotation.WorkerThread
import io.reactivex.rxjava3.core.Observable

interface DataRequestManager {

    @WorkerThread
    fun <T> getData(request: DataRequest<T>): Observable<DataResponse<T>>
}