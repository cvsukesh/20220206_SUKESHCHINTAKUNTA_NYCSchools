package com.codingchallenge.nycschools

import android.app.Application
import com.codingchallenge.nycschools.utils.AppLibUtils
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class with android hilt entry point.
 */
@HiltAndroidApp
class NYCSchoolApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppLibUtils.get().setApplicationContext(this)
    }
}