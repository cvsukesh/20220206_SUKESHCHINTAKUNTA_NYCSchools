package com.codingchallenge.nycschools.room.model

import android.text.TextUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlin.text.StringBuilder
@Entity(tableName = "nyc_high_school")
data class NycSchool(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("dbn")
    val id: String,
    @SerializedName("school_name")
    val highSchoolName: String,
    @SerializedName("num_of_sat_test_takers")
    val totalSatTestTakers: String?,
    @SerializedName("sat_critical_reading_avg_score")
    val satCriticalAvgScore: String?,
    @SerializedName("sat_math_avg_score")
    val satAvgMathScore: String?,
    @SerializedName("sat_writing_avg_score")
    val satWritingAvgScore: String?,
    @SerializedName("overview_paragraph")
    val description: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("fax_number")
    val faxNumber: String?,
    @SerializedName("school_email")
    val schoolEmail: String?,
    @SerializedName("website")
    val website: String?,
    @SerializedName("total_students")
    val totalStudents: String?,
    @SerializedName("extracurricular_activities")
    val activities: String?,
    @SerializedName("school_sports")
    val schoolSports: String?,
    val academicopportunities1: String?,
    val academicopportunities2: String?,
    val academicopportunities3: String?,
    val academicopportunities4: String?,
    val academicopportunities5: String?,
    val admissionspriority11: String?,
    val admissionspriority21: String?,
    val admissionspriority31: String?,
    val admissionspriority41: String?,
    val admissionspriority51: String?,
    @SerializedName("primary_address_line_1")
    val addressLine1: String?,
    val city: String?,
    val zip: String?,
    @SerializedName("state_code")
    val stateCode: String?
) {
    fun getHighSchoolLocation(): String {
        val stringBuilder = StringBuilder()
        city?.let {
            stringBuilder.append(it)
        }
        stateCode?.let {
            stringBuilder.append(" $it")
        }
        return stringBuilder.toString()
    }

    fun getCriticalAvgScore(): String = satCriticalAvgScore?:"NA"
    fun getAvgMathScore(): String = satAvgMathScore?:"NA"
    fun getWritingAvgScore(): String = satWritingAvgScore?:"NA"
}

