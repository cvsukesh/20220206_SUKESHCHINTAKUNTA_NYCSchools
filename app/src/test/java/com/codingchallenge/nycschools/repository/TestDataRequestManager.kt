package com.codingchallenge.nycschools.repository

import com.codingchallenge.nycschools.network.DataError
import com.codingchallenge.nycschools.network.DataRequest
import com.codingchallenge.nycschools.network.DataRequestManager
import com.codingchallenge.nycschools.network.DataResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class TestDataRequestManager : DataRequestManager {
    override fun <T> getData(request: DataRequest<T>): Observable<DataResponse<T>> {
        val observable = Observable.create<DataResponse<T>> { emitter ->
            request.getNetworkObservable().subscribe({ response ->
                emitter.onNext(DataResponse.SUCCESS(response))
                emitter.onComplete()
            }, {
                emitter.onNext(DataResponse.ERROR(DataError("", "Error", "Error")))
                emitter.onComplete()
            })
        }

        observable.subscribeOn(Schedulers.io())
        observable.observeOn(AndroidSchedulers.mainThread())
        return observable
    }
}