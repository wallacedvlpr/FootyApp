package com.example.footyapp.view.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.db.Favorite

class FavoritesAdapter (private val activity: FragmentActivity):
    RecyclerView.Adapter<FavItemViewHolder>(){
    var dataSet: List<Favorite> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavItemViewHolder {
        return FavItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.favorites_frag_list_item_layout,
                    parent,
                    false
                )
        )
    }
    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: FavItemViewHolder, position: Int) {
        holder.onBind(dataSet[position], activity)
    }
}//endOf Adapter Class