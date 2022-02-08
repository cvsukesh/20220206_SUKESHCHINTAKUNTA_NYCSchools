package com.codingchallenge.nycschools.utils

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import com.codingchallenge.nycschools.R
import com.codingchallenge.nycschools.room.model.NycSchool

object AppUtils {

    fun getInstance(): AppUtils = this

    fun getTotalStudents(nycSchool: NycSchool?): String  {
        nycSchool?.totalStudents?.let {
            return AppLibUtils.get().getString(R.string.total_students, it)
        }
        return ""
    }

    fun getAverageMathScore(nycSchool: NycSchool?): Spanned {
        StringBuilder().apply {
            append(AppLibUtils.get().getString(R.string.sat_average))
            val bulletOneStartIndex = length
            append(
                AppLibUtils.get().getString(R.string.sat_avg_math_score, nycSchool?.getAvgMathScore())
            )
            val bulletOneEndIndex = length
            append(
                AppLibUtils.get().getString(
                    R.string.sat_critical_reading_avg_score,
                    nycSchool?.getCriticalAvgScore()
                )
            )
            val bulletTwoEndIndex = length
            append(
                AppLibUtils.get()
                    .getString(R.string.sat_writing_avg_score, nycSchool?.getWritingAvgScore())
            )
            val bulletThreeEndIndex = length
            also {
                SpannableStringBuilder(this).apply {
                    setSpan(
                        BulletSpan(30),
                        bulletOneStartIndex,
                        bulletOneEndIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        BulletSpan(30),
                        bulletOneEndIndex,
                        bulletTwoEndIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        BulletSpan(30),
                        bulletTwoEndIndex,
                        bulletThreeEndIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    return this
                }
            }
        }
    }
}