package com.example.footyapp.view.fragments


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem

class ClubAdapter:
    RecyclerView.Adapter<ClubItemViewHolder>(){


    var activity: FragmentActivity? = null
    var dataSet: List<ClubItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubItemViewHolder {
        return ClubItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.clubs_frag_list_item_layout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ClubItemViewHolder, position: Int) {
        holder.onBind(dataSet[position], activity)
    }
}//endOf Adapter Class
