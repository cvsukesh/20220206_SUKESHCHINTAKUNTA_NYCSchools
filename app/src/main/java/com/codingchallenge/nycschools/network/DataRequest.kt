package com.codingchallenge.nycschools.network

import io.reactivex.rxjava3.core.Observable

abstract class DataRequest<T> {

    abstract fun getNetworkObservable() : Observable<T>
}