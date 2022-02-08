package com.codingchallenge.nycschools.network

sealed interface DataResponse<T> {
    data class SUCCESS<T>(val value: T) : DataResponse<T>
    data class ERROR<T>(val dataError: DataError) : DataResponse<T>
}
