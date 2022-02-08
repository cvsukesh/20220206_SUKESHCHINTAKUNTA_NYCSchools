package com.codingchallenge.nycschools.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.nycschools.R
import com.codingchallenge.nycschools.databinding.ListItemViewHolderBinding
import com.codingchallenge.nycschools.room.model.NycSchool
import com.codingchallenge.nycschools.view.MainActivity

class NycHighSchoolAdapter(val activity: MainActivity, var listOfHighSchool: ArrayList<NycSchool>) :
    RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        val binding: ListItemViewHolderBinding =
            DataBindingUtil.inflate(inflater, R.layout.list_item_view_holder, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val nycSchool = listOfHighSchool.get(position)
        holder.bind(nycSchool)

        holder.binding.cardView.setOnClickListener {
            activity.launchDetailScreen(nycSchool.id)
        }
    }

    override fun getItemCount(): Int {
        return listOfHighSchool.size
    }

}

class ItemViewHolder internal constructor(val binding: ListItemViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(nycSchool: NycSchool) {
        binding.nycSchool = nycSchool
    }
}