package com.codingchallenge.nycschools.utils

import android.content.Context
import com.codingchallenge.nycschools.R
import java.lang.ref.WeakReference

class AppLibUtils private constructor() {

    private lateinit var mContextRef:WeakReference<Context>

    fun setApplicationContext(context: Context) {
        mContextRef = WeakReference(context)
    }

    fun getContext() {
        mContextRef
    }

    fun getString(resId: Int): String {
        return mContextRef.get()?.resources?.getString(resId)?:""
    }

    fun getString(resId: Int, vararg formatArgs: Any?): String {
        return mContextRef.get()?.resources?.getString(resId, *formatArgs)?:""
    }

    fun isTabletDevice() : Boolean {
        return mContextRef.get()?.resources?.getBoolean(R.bool.isTablet)?:false
    }

    companion object {
        private val INSTANCE = AppLibUtils()

        @JvmStatic
        @Synchronized
        fun get(): AppLibUtils {
            return INSTANCE
        }
    }
}