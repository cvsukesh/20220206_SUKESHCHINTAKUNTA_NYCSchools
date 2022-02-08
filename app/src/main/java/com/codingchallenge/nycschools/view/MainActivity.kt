package com.codingchallenge.nycschools.view

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codingchallenge.nycschools.R
import com.codingchallenge.nycschools.databinding.ActivityMainBinding
import com.codingchallenge.nycschools.mvvm.NycHighSchoolViewModel
import com.codingchallenge.nycschools.utils.AppConstants
import com.codingchallenge.nycschools.utils.AppLibUtils
import com.codingchallenge.nycschools.view.fragment.NycHighSchoolDetailFragment
import com.codingchallenge.nycschools.view.fragment.NycHighSchoolListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NycHighSchoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = if (AppLibUtils.get().isTabletDevice()) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_main,
            null,
            false
        )
        setContentView(binding.root)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[NycHighSchoolViewModel::class.java]
        binding.viewModel = viewModel

        initObservers()
        viewModel.getListOfSchools()
    }

    private fun initObservers() {

        viewModel.loading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.errorStatus.observe(this, {
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorTitle.text = it.errorTitle
            binding.errorMessage.text = it.errorMessage
        })

        viewModel.apiSuccessStatus.observe(this, {
            launchFragment(NycHighSchoolListFragment(), AppConstants.FRAGMENT_LIST_OF_SCHOOLS)
        })
    }

    private fun launchFragment(fragment: Fragment, tag: String = "") {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(null).commit()
    }

    fun launchDetailScreen(id: String) {
        if (AppLibUtils.get().isTabletDevice()) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_detail_view,
                NycHighSchoolDetailFragment.newInstance(id),
                AppConstants.FRAGMENT_DETAIL_SCREEN
            ).commit()
        } else {
            val intent = Intent(this, HighSchoolDetailActivity::class.java)
            intent.putExtra(AppConstants.EXTRAS_ID, id)
            startActivity(intent)
        }
    }
}
