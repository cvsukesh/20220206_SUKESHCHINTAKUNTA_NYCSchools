package com.codingchallenge.nycschools.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingchallenge.nycschools.databinding.FragmentListNycHighSchoolsBinding
import com.codingchallenge.nycschools.mvvm.NycHighSchoolViewModel
import com.codingchallenge.nycschools.R
import com.codingchallenge.nycschools.databinding.FragmentDetailNycHighSchoolBinding
import com.codingchallenge.nycschools.utils.AppConstants
import com.codingchallenge.nycschools.utils.AppUtils
import com.codingchallenge.nycschools.view.adapter.NycHighSchoolAdapter

class NycHighSchoolDetailFragment : Fragment() {

    private lateinit var nycHighSchoolViewModel: NycHighSchoolViewModel

    private lateinit var binding: FragmentDetailNycHighSchoolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail_nyc_high_school,
            container,
            false
        )
        nycHighSchoolViewModel =
            ViewModelProvider(requireActivity())[NycHighSchoolViewModel::class.java]
        binding.lifecycleOwner = this
        binding.appUtils = AppUtils.getInstance()
        initObservers(arguments?.getString(AppConstants.EXTRAS_ID)?:"")
        return binding.root
    }

    private fun initObservers(id: String) {
        nycHighSchoolViewModel.getHighSchoolBasedOnId.observe(viewLifecycleOwner, {
            binding.nycSchool = it
        })
        nycHighSchoolViewModel.getHighSchoolFromId(id)
    }

    companion object {
        fun newInstance(id: String): NycHighSchoolDetailFragment {
            return NycHighSchoolDetailFragment().apply {
                arguments = Bundle().apply { putString(AppConstants.EXTRAS_ID, id) }
            }
        }
    }
}