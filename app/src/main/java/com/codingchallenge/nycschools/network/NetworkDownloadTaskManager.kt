package com.codingchallenge.nycschools.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.codingchallenge.nycschools.R
import com.codingchallenge.nycschools.utils.AppLibUtils

class NetworkDownloadTaskManager(@ApplicationContext var context: Context) : DataRequestManager {


    override fun <T> getData(request: DataRequest<T>): Observable<DataResponse<T>> =
        Observable.create<DataResponse<T>> { emitter ->
            if (!isInternetAvailable(context)) {
                val dataError = DataError(
                    "",
                    AppLibUtils.get().getString(R.string.no_network),
                    AppLibUtils.get().getString(R.string.network_error_message)
                )
                emitter.onNext(DataResponse.ERROR(dataError))
                emitter.onComplete()
            } else {
                request.getNetworkObservable().subscribe({ response ->
                    emitter.onNext(DataResponse.SUCCESS(response))
                    emitter.onComplete()
                }, {
                    val dataError =
                        DataError(
                            "",
                            AppLibUtils.get().getString(R.string.default_error),
                            AppLibUtils.get().getString(R.string.default_error_message)
                        )
                    emitter.onNext(DataResponse.ERROR(dataError))
                    emitter.onComplete()
                })
            }
        }.apply {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
        }


    private fun isInternetAvailable(@ApplicationContext context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }
}