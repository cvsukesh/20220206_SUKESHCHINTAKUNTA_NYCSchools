package com.codingchallenge.nycschools.network

import java.lang.Exception

data class DataError(val errorCode: String?, val errorTitle: String?, val errorMessage: String?) : Exception()