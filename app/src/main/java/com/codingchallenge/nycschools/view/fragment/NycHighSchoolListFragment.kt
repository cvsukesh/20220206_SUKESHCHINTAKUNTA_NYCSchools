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
import com.codingchallenge.nycschools.view.MainActivity
import com.codingchallenge.nycschools.view.adapter.NycHighSchoolAdapter

class NycHighSchoolListFragment : Fragment() {

    private lateinit var nycHighSchoolViewModel: NycHighSchoolViewModel

    private lateinit var binding: FragmentListNycHighSchoolsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        nycHighSchoolViewModel = ViewModelProvider(requireActivity())[NycHighSchoolViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list_nyc_high_schools,
            container,
            false
        )
        binding.lifecycleOwner = this
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        nycHighSchoolViewModel.getRecordsData.observe(viewLifecycleOwner, {
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = NycHighSchoolAdapter(activity as MainActivity, it)
            }
        })
        nycHighSchoolViewModel.getNycHighSchoolsFromDB()
    }
}