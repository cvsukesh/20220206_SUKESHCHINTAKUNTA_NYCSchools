package com.codingchallenge.nycschools.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.codingchallenge.nycschools.R
import com.codingchallenge.nycschools.network.DataError
import com.codingchallenge.nycschools.network.DataResponse
import com.codingchallenge.nycschools.room.model.NycSchool
import com.codingchallenge.nycschools.utils.AppLibUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NycHighSchoolViewModel @Inject constructor(
    var nycSchoolsRepository: NycSchoolsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val TAG = "NycHighSchoolViewModel"
    }

    private val compositeDisposable = CompositeDisposable()

    // Livedata to observe to display loader symbol.
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)

    // Livedata to observe Errors from API
    val errorStatus: MutableLiveData<DataError> = MutableLiveData()

    // Livedata to observe api changes
    private val apiSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val apiSuccessStatus: LiveData<Boolean> = apiSuccess

    // LiveData to get high schools
    private val allRecords: MutableLiveData<ArrayList<NycSchool>> = MutableLiveData()
    val getRecordsData: LiveData<ArrayList<NycSchool>> = allRecords

    // LiveData to retrieve the data for detail screen from DB
    private val record: MutableLiveData<NycSchool> = MutableLiveData()
    val getHighSchoolBasedOnId: LiveData<NycSchool> = record

    /**
     * API to get the list of NYC high school names.
     */
    fun getListOfSchools() {
        loading.postValue(true)
        val disposable = nycSchoolsRepository.getNycHighSchoolRecords().subscribe({ dataResponse ->
            when (dataResponse) {
                is DataResponse.SUCCESS -> {
                    Log.d(TAG, "High School List DataResponse.SUCCESS " + dataResponse.value)
                    nycSchoolsRepository.insertNycHighSchoolRecords(dataResponse.value)
                    getSatRecordsForNycHighSchool()
                }
                is DataResponse.ERROR -> {
                    Log.e(TAG, "High School List DataResponse.ERROR ")
                    showGenericError(dataResponse.dataError)
                }
            }
        }, {
            Log.e(TAG, "High School List Exception $it")
            showGenericError(
                DataError(
                    "",
                    AppLibUtils.get().getString(R.string.default_error),
                    AppLibUtils.get().getString(R.string.default_error_message)
                )
            )
        })

        compositeDisposable.add(disposable)
    }

    /**
     * Method will get the SAT scores for the high schools
     * and will update data in DB
     */
    private fun getSatRecordsForNycHighSchool() {
        val disposable =
            nycSchoolsRepository.getSatRecordsForNycHighSchool().subscribe({ dataResponse ->
                loading.postValue(false)
                when (dataResponse) {
                    is DataResponse.SUCCESS -> {
                        Log.d(
                            TAG,
                            "High School SAT List DataResponse.SUCCESS " + dataResponse.value
                        )
                        nycSchoolsRepository.updateNycSchoolRecords(dataResponse.value)
                        apiSuccess.postValue(true)
                    }
                    is DataResponse.ERROR -> {
                        Log.e(TAG, "High School SAT List DataResponse.ERROR ")
                        showGenericError(dataResponse.dataError)
                    }
                }
            }, {
                Log.e(TAG, "High School SAT List Exception $it")
                showGenericError(
                    DataError(
                        "",
                        AppLibUtils.get().getString(R.string.default_error),
                        AppLibUtils.get().getString(R.string.default_error_message)
                    )
                )
            })

        compositeDisposable.add(disposable)
    }

    /**
     * Method to get list of high school details from DB
     */
    fun getNycHighSchoolsFromDB() {
        val disposable = nycSchoolsRepository.getAllRecords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                allRecords.postValue(it as ArrayList<NycSchool>)
            }, {})
        compositeDisposable.add(disposable)
    }

    /**
     * Method to get Nyc School based on ID.
     */
    fun getHighSchoolFromId(id: String) {
        val disposable = nycSchoolsRepository.getHighSchool(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                record.postValue(it)
            }, {})

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    /**
     * Method will display error cases.
     */
    private fun showGenericError(dataError: DataError) {
        loading.postValue(false)
        errorStatus.postValue(dataError)
    }
}