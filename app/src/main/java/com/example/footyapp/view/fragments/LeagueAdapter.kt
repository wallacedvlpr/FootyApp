package com.example.footyapp.view.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.League

class LeagueAdapter(private val fragmentActivity: FragmentActivity): RecyclerView.Adapter<LeagueItemViewHolder>() {

    var dataSet: List<League> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueItemViewHolder {
        return LeagueItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.leagues_frag_list_item_layout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = dataSet.size
    override fun onBindViewHolder(holder: LeagueItemViewHolder, position: Int) {
        holder.onBind(dataSet[position], position, fragmentActivity)
    }

}//endOf Adapter class