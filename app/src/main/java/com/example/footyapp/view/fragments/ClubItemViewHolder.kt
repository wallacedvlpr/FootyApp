package com.example.footyapp.view.fragments

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.footyapp.R
import com.example.footyapp.model.ClubItem
import com.example.footyapp.view.activities.DetailedClubActivity

class ClubItemViewHolder(itemView: View):
    RecyclerView.ViewHolder(itemView){

    private val tvClubName: TextView
            = itemView.findViewById(R.id.club_name_club_item_layout)

    fun onBind(c: ClubItem, fragmentActivity: FragmentActivity?){

        tvClubName.text = c.english_name

        itemView.setOnClickListener {
           fragmentActivity?.let { activity ->
               val newIntent = Intent(activity, DetailedClubActivity::class.java)
               activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
               newIntent.putExtra("Club_ID", c.id)
               activity.startActivity(newIntent)
           }
        }
    }
}//endOf ItemViewHolder