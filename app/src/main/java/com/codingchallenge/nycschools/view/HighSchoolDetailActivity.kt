package com.codingchallenge.nycschools.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codingchallenge.nycschools.R
import com.codingchallenge.nycschools.utils.AppConstants
import com.codingchallenge.nycschools.view.fragment.NycHighSchoolDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HighSchoolDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_detail)

        val id: String = intent.getStringExtra(AppConstants.EXTRAS_ID) ?: ""
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_detail_view,
            NycHighSchoolDetailFragment.newInstance(id),
            AppConstants.FRAGMENT_DETAIL_SCREEN
        ).commit()
    }
}